package org.studing.parsing.writer.habr.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import lombok.NonNull;
import lombok.val;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListConverter implements Converter {
    @Override
    public Object unmarshal(
        @NonNull final HierarchicalStreamReader hierarchicalStreamReader,
        @NonNull final UnmarshallingContext unmarshallingContext) {

        val list = new ArrayList<>();
        hierarchicalStreamReader.moveDown();
        while (hierarchicalStreamReader.hasMoreChildren()) {
            hierarchicalStreamReader.moveDown();
            list.add(unmarshallingContext.convertAnother(list, Object.class));
            hierarchicalStreamReader.moveUp();
        }
        hierarchicalStreamReader.moveUp();
        return list;
    }

    @Override
    public boolean canConvert(@NonNull final Class aClass) {
        return List.class.isAssignableFrom(aClass);
    }
}
