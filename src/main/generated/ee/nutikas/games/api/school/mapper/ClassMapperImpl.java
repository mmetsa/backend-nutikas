package ee.nutikas.games.api.school.mapper;

import ee.nutikas.games.api.school.dto.ClassResponse;
import ee.nutikas.games.api.school.model.ClassModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:11+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class ClassMapperImpl implements ClassMapper {

    @Override
    public ClassResponse toResponse(ClassModel model) {
        if ( model == null ) {
            return null;
        }

        ClassResponse classResponse = new ClassResponse();

        classResponse.setId( model.getId() );
        classResponse.setName( model.getName() );

        return classResponse;
    }

    @Override
    public List<ClassResponse> toResponseList(List<ClassModel> models) {
        if ( models == null ) {
            return null;
        }

        List<ClassResponse> list = new ArrayList<ClassResponse>( models.size() );
        for ( ClassModel classModel : models ) {
            list.add( toResponse( classModel ) );
        }

        return list;
    }
}
