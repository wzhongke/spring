package com.wang.action.command;

/**
 * PasteCommand 需要一个 Document 对象作为其接收者
 */
public class PasteCommand extends Command {
	Document document;

	public PasteCommand(Document document) {
		this.document = document;
	}

	@Override
	public void execute() {
		document.paste();
	}
}

class Document {
	void paste () {
		System.out.println("Paste content! ");
	}
}