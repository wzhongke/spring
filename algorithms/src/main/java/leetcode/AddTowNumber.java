package leetcode;

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
}

public class AddTowNumber {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode resultNode = new ListNode(0);
		ListNode nextNode = resultNode;
		int exp = 0;
		while(l1 != null && l2 != null) {
			int sum = l1.val + l2.val + exp;
			nextNode.next = new ListNode(sum % 10);
			nextNode = nextNode.next;
			exp = sum / 10;
			l1 = l1.next;
			l2 = l2.next;
		}
		ListNode l = l1 == null ? l2 : l1;
		while(l != null) {
			nextNode.next = new ListNode(l.val + exp);
			nextNode = nextNode.next;
			l = l.next;
			exp = 0;
		}

		return resultNode.next;
	}

	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		l1.next = new ListNode(4);
		l1.next.next = new ListNode(3);
		ListNode l2 = new ListNode(5);
		l2.next = new ListNode(6);
		l2.next.next = new ListNode(4);
		l1 = new AddTowNumber().addTwoNumbers(l1, l2);
		while (l1 != null) {
			System.out.println(l1.val);
			l1 = l1.next;
		}
	}
}
