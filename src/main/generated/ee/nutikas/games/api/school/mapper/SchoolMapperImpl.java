package ee.nutikas.games.api.school.mapper;

import ee.nutikas.games.api.school.dto.SchoolDTO;
import ee.nutikas.games.api.school.dto.SchoolRequest;
import ee.nutikas.games.api.school.dto.SchoolResponse;
import ee.nutikas.games.api.school.model.SchoolModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-22T22:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Eclipse Adoptium)"
)
public class SchoolMapperImpl implements SchoolMapper {

    private final ClassMapper classMapper = ClassMapper.INSTANCE;

    @Override
    public SchoolDTO toDTO(SchoolRequest request) {
        if ( request == null ) {
            return null;
        }

        SchoolDTO schoolDTO = new SchoolDTO();

        schoolDTO.setName( request.getName() );
        schoolDTO.setShortName( request.getShortName() );
        schoolDTO.setAddress( request.getAddress() );
        schoolDTO.setEmail( request.getEmail() );
        schoolDTO.setPhone( request.getPhone() );

        return schoolDTO;
    }

    @Override
    public SchoolDTO toDTO(SchoolModel model) {
        if ( model == null ) {
            return null;
        }

        SchoolDTO schoolDTO = new SchoolDTO();

        schoolDTO.setId( model.getId() );
        schoolDTO.setName( model.getName() );
        schoolDTO.setShortName( model.getShortName() );
        schoolDTO.setAddress( model.getAddress() );
        schoolDTO.setEmail( model.getEmail() );
        schoolDTO.setPhone( model.getPhone() );

        return schoolDTO;
    }

    @Override
    public SchoolResponse toResponse(SchoolDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SchoolResponse schoolResponse = new SchoolResponse();

        schoolResponse.setId( dto.getId() );
        schoolResponse.setName( dto.getName() );
        schoolResponse.setShortName( dto.getShortName() );
        schoolResponse.setAddress( dto.getAddress() );
        schoolResponse.setEmail( dto.getEmail() );
        schoolResponse.setPhone( dto.getPhone() );

        return schoolResponse;
    }

    @Override
    public SchoolResponse toResponse(SchoolModel model) {
        if ( model == null ) {
            return null;
        }

        SchoolResponse schoolResponse = new SchoolResponse();

        schoolResponse.setId( model.getId() );
        schoolResponse.setName( model.getName() );
        schoolResponse.setShortName( model.getShortName() );
        schoolResponse.setAddress( model.getAddress() );
        schoolResponse.setEmail( model.getEmail() );
        schoolResponse.setPhone( model.getPhone() );
        schoolResponse.setClasses( classMapper.toResponseList( model.getClasses() ) );

        return schoolResponse;
    }

    @Override
    public List<SchoolResponse> toResponseList(List<SchoolModel> models) {
        if ( models == null ) {
            return null;
        }

        List<SchoolResponse> list = new ArrayList<SchoolResponse>( models.size() );
        for ( SchoolModel schoolModel : models ) {
            list.add( toResponse( schoolModel ) );
        }

        return list;
    }

    @Override
    public SchoolModel toModel(SchoolDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SchoolModel schoolModel = new SchoolModel();

        schoolModel.setId( dto.getId() );
        schoolModel.setName( dto.getName() );
        schoolModel.setShortName( dto.getShortName() );
        schoolModel.setAddress( dto.getAddress() );
        schoolModel.setEmail( dto.getEmail() );
        schoolModel.setPhone( dto.getPhone() );

        return schoolModel;
    }
}
