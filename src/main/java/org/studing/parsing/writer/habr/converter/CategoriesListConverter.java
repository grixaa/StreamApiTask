package org.studing.parsing.writer.habr.converter;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class CategoriesListConverter extends BaseListConverter {
    boolean needToAddTagCategories;

    @Override
    public void marshal(
        @NonNull final Object o,
        @NonNull final HierarchicalStreamWriter hierarchicalStreamWriter,
        @NonNull final MarshallingContext marshallingContext) {

        if (needToAddTagCategories) {
            hierarchicalStreamWriter.startNode("categories");
        }

        val list = (List<?>) o;
        for (val item : list) {
            hierarchicalStreamWriter.startNode("category");
            marshallingContext.convertAnother(item);
            hierarchicalStreamWriter.endNode();
        }

        if (needToAddTagCategories) {
            hierarchicalStreamWriter.endNode();
        }
    }
}
