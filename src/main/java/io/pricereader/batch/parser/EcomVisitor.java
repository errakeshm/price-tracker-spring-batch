package io.pricereader.batch.parser;

public interface EcomVisitor {
	Item visit(UrlElement element);
}