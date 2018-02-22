package com.ethanaa.differ;

import com.ethanaa.differ.model.*;
import de.danielbechler.diff.NodeQueryService;
import de.danielbechler.diff.ObjectDiffer;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.comparison.ComparisonStrategy;
import de.danielbechler.diff.differ.Differ;
import de.danielbechler.diff.differ.DifferDispatcher;
import de.danielbechler.diff.differ.DifferFactory;
import de.danielbechler.diff.node.DiffNode;
import de.danielbechler.diff.node.PrintingVisitor;
import de.danielbechler.diff.node.Visit;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;

@SpringBootApplication
public class DifferApplication implements CommandLineRunner {

	private ObjectDiffer differ = ObjectDifferBuilder.startBuilding()
			.comparison()
			  .ofType(LocalDateTime.class).toUseEqualsMethod()
			  .ofType(LocalDate.class).toUseEqualsMethod()
			.and()
			.build();

	private EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
			.build();

	public static void main(String[] args) {

		SpringApplication.run(DifferApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {

		System.out.println("Welcome to Differ.");

		List<SourceData> sourceDataList = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			sourceDataList.add(buildSourceData(i));
		}

		Collection<MasterData> masteredData = master(sourceDataList);
	}

	private Collection<MasterData> master(Collection<SourceData> sourceData) {
		return sourceData.stream()
				.collect(Collectors.groupingBy(SourceData::getSourceId,
						Collectors.reducing(
								new MasterData(),
								MasterData::new,
								(agg, inc) -> merge(inc, new MasterData(agg), agg))))
				.values();
	}

	private MasterData merge(MasterData modified, MasterData base, MasterData head) {

		final DiffNode.Visitor merger = new MergingDifferenceVisitor<>(head, modified);
		final DiffNode.Visitor printer = new PrintingVisitor(modified, base);

		final DiffNode diff = differ.compare(modified, base);

		diff.visit(merger);
		diff.visit(printer);

		return head;
	}

	private static final class MergingDifferenceVisitor<T> implements DiffNode.Visitor {

		private final T head;
		private final T modified;

		public MergingDifferenceVisitor(final T head, final T modified) {

			this.head = head;
			this.modified = modified;
		}

		public void node(final DiffNode node, final Visit visit) {

			if (node.getState() == DiffNode.State.ADDED) {
				node.canonicalSet(head, node.canonicalGet(modified));
			}
			else if (node.getState() == DiffNode.State.REMOVED) {
				node.canonicalUnset(head);
			}
			else if (node.getState() == DiffNode.State.CHANGED) {
				if (node.hasChildren()) {
					node.visitChildren(this);
					visit.dontGoDeeper();
				}
				else {
					node.canonicalSet(head, node.canonicalGet(modified));
				}
			}
		}
	}

	private SourceData buildSourceData(int i) {

		Random random = new Random(System.nanoTime());

		SourceData sourceData = new SourceData();
		sourceData.setSourceId(String.valueOf(1));
		sourceData.setSource(Source.values()[random.nextInt(Source.values().length)]);
		sourceData.setCustomer(Person.random(enhancedRandom));//random.nextBoolean() ? Person.random(enhancedRandom) : Organization.random(enhancedRandom));

		return sourceData;
	}
}
