import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Implementation of a non-increasing monotonic queue for certain (but common) use case:
 * When larger objects pushed to the queue are able to replace and represent smaller objects that come before it.
 * Callable methods are:
 * isEmpty()
 * max()
 * pop()
 * push()
 * @param <T> generic type for objects to be stored or queried   
 */
class MonotonicQueue<T extends Comparable<T>> {
  private Deque<Pair<T>> dq = new ArrayDeque<>(); // or LinkedList

  /**
   * Checks if queue is empty.
   * @return boolean
   */
  public boolean isEmpty() {
    return dq.isEmpty();
  }

  /**
   * Push an object into the queue and maintain the non-increasing property.
   * @param T obj to be inserted
   */
  public void push(T obj) {
    Integer count = 0;
    while (!dq.isEmpty() && obj.compareTo(dq.peekLast().value) >= 0) {
      Pair<T> popped = dq.pollLast();
      // accumulate count of objects that were popped to maintain the non-increasing property
      count += 1 + popped.countDeleted; 
    }
    dq.offerLast(new Pair<T>(obj, count));
  }

  /**
   * Returns the highest value stored in the queue.
   * @return the first value of the queue.
   */
  public T max() {
    if (isEmpty()) {
      return null;
    }
    return dq.peek().value;
  }

  /**
   * Returns the highest valued object in the queue; i.e the first object;
   * Removal will only be done all representation of the object has been accounted for.
   */
  public T pop() {
    Pair<T> node = dq.peek();
    if (node.countDeleted > 0) {
      node.countDeleted -= 1;
      return node.value;
    }
    dq.poll();
    return node.value;
  }

  /**
   * Node class that is represented as a pair.
   * Tracks the deleted count and the value to be wrapped.
   */
  private static class Pair<T> {
    private T value;
    private Integer countDeleted;

    private Pair(T val, Integer count) {
      this.value = val;
      this.countDeleted = count;
    }
  }
}
