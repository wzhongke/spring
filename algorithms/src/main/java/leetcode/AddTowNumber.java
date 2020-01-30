package leetcode;

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
}

public class AddTowNumber {
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode dummyHead = new ListNode(0);
		ListNode p = l1, q = l2, curr = dummyHead;
		int carry = 0;
		while (p != null || q != null) {
			int x = (p != null) ? p.val : 0;
			int y = (q != null) ? q.val : 0;
			int sum = carry + x + y;
			carry = sum / 10;
			curr.next = new ListNode(sum % 10);
			curr = curr.next;
			if (p != null) p = p.next;
			if (q != null) q = q.next;
		}
		if (carry > 0) {
			curr.next = new ListNode(carry);
		}
		return dummyHead.next;
	}

	public static void main(String[] args) {
		ListNode l1 = new ListNode(9);
		l1.next = new ListNode(9);
		ListNode l2 = new ListNode(1);
		l1 = new AddTowNumber().addTwoNumbers(l1, l2);
		while (l1 != null) {
			System.out.println(l1.val);
			l1 = l1.next;
		}
	}
}
