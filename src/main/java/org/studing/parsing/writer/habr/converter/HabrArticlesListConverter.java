package org.studing.parsing.writer.habr.converter;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.NonNull;
import lombok.val;

import java.util.List;

public class HabrArticlesListConverter extends BaseListConverter {
    @Override
    public void marshal(
        @NonNull final Object o,
        @NonNull final HierarchicalStreamWriter hierarchicalStreamWriter,
        @NonNull final MarshallingContext marshallingContext) {

        val list = (List<?>) o;
        hierarchicalStreamWriter.startNode("articles");
        for (val item : list) {
            hierarchicalStreamWriter.startNode("article");
            marshallingContext.convertAnother(item);
            hierarchicalStreamWriter.endNode();
        }
        hierarchicalStreamWriter.endNode();
    }
}
