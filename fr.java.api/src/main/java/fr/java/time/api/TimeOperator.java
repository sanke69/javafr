package fr.java.time.api;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import fr.java.time.Time;

@FunctionalInterface
public interface TimeOperator<OP extends TimeOperation> {

	public Optional<OP> getNextOperation(Instant _from);

	public static final class Utils {

		public static <TO extends TimeOperation> TO 			getNextOperation(TimeOperator<TO> _operator, Instant _from) {
			return _operator.getNextOperation(_from).orElse(null);
		}
		public static <TO extends TimeOperation> TO 			getNextOperation(TimeOperator<TO> _operator, Instant _from, Instant _to) {
			Optional<TO> next_op = _operator.getNextOperation(_from);

			if (next_op.isPresent()) {
				Instant term = next_op.get().getTerm();

				if (term.equals(_to) || term.isBefore(_to))
					return next_op.get();
			}

			return null;
		}
		public static <TO extends TimeOperation> TO 			getNextOperation(TimeOperator<TO> _operator, Instant _from, Duration _during) {
			return getNextOperation(_operator, _from, Time.add(_from, _during));
		}
		public static <TO extends TimeOperation> TO 			getNextOperation(TimeOperator<TO> _operator, Instant _from, Period _during) {
			return getNextOperation(_operator, _from, Time.add(_from, _during));
		}

		public static <TO extends TimeOperation> Collection<TO> getNextOperations(TimeOperator<TO> _operator, Instant _from, Instant _to) {
			List<TO> operations = new ArrayList<TO>();

			Optional<TO> next_op = _operator.getNextOperation(_from);
			while (next_op.isPresent()) {
				Instant term = next_op.get().getTerm();

				if (term.equals(_to) || term.isBefore(_to))
					operations.add(next_op.get());
				else
					break;

				next_op = _operator.getNextOperation(Time.addNanos(term, 1));
			}

			return operations;
		}
		public static <TO extends TimeOperation> Collection<TO> getNextOperations(TimeOperator<TO> _operator, Instant _from, Duration _during) {
			return getNextOperations(_operator, _from, Time.add(_from, _during));
		}
		public static <TO extends TimeOperation> Collection<TO> getNextOperations(TimeOperator<TO> _operator, Instant _from, Period _during) {
			return getNextOperations(_operator, _from, Time.add(_from, _during));
		}

		public static <TO extends TimeOperation> Collection<TO> getNextOperations(Collection<TimeOperator<TO>> _operators, Instant _from) {
			List<TO> operations = new ArrayList<TO>();
			Instant term = null;

			for (TimeOperator<TO> op : _operators) {
				Optional<TO> next_op = op.getNextOperation(_from);

				if (next_op.isPresent()) {
					TO co = next_op.get();
					if (operations.isEmpty()) {
						term = co.getTerm();
						operations.add(co);
					} else {
						Instant co_term = co.getTerm();
						if (co_term.isBefore(term)) {
							operations.clear();

							term = co.getTerm();
							operations.add(co);
						} else if (co_term.equals(term)) {
							operations.add(co);
						}
					}
				}
			}

			return operations;
		}

		public static <TO extends TimeOperation> Collection<TO> getNextOperations(Collection<TimeOperator<TO>> _operators, Instant _from, Instant _to) {
			List<TO> operations = new ArrayList<TO>();

			for (TimeOperator<TO> op : _operators) {

				Optional<TO> next_op = op.getNextOperation(_from);
				while (next_op.isPresent()) {
					Instant term = next_op.get().getTerm();
					if (term.equals(_to) || term.isBefore(_to))
						operations.add(next_op.get());
					else
						break;

					next_op = op.getNextOperation(Time.addNanos(term, 1));
				}

			}

			return operations;
		}
		public static <TO extends TimeOperation> Collection<TO> getNextOperations(Collection<TimeOperator<TO>> _operators, Instant _from, Duration _during) {
			return getNextOperations(_operators, _from, Time.add(_from, _during));
		}
		public static <TO extends TimeOperation> Collection<TO> getNextOperations(Collection<TimeOperator<TO>> _operators, Instant _from, Period _during) {
			return getNextOperations(_operators, _from, Time.add(_from, _during));
		}

	}

}
