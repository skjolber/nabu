package no.rutebanken.nabu.organisation.rest;

import io.swagger.annotations.Api;
import no.rutebanken.nabu.organisation.model.CodeSpace;
import no.rutebanken.nabu.organisation.repository.CodeSpaceRepository;
import no.rutebanken.nabu.organisation.repository.VersionedEntityRepository;
import no.rutebanken.nabu.organisation.rest.dto.CodeSpaceDTO;
import no.rutebanken.nabu.organisation.rest.mapper.CodeSpaceMapper;
import no.rutebanken.nabu.organisation.rest.mapper.DTOMapper;
import no.rutebanken.nabu.organisation.rest.validation.CodeSpaceValidator;
import no.rutebanken.nabu.organisation.rest.validation.DTOValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static org.rutebanken.helper.organisation.AuthorizationConstants.ROLE_ORGANISATION_EDIT;

@Component
@Produces("application/json")
@Path("/code_spaces")
@Transactional
@PreAuthorize("hasRole('" + ROLE_ORGANISATION_EDIT + "')")
@Api
public class CodeSpaceResource extends AnnotatedBaseResource<CodeSpace, CodeSpaceDTO> {


	@Autowired
	private CodeSpaceRepository repository;

	@Autowired
	private CodeSpaceValidator validator;

	@Autowired
	private CodeSpaceMapper mapper;


	@Override
	protected VersionedEntityRepository<CodeSpace> getRepository() {
		return repository;
	}

	@Override
	protected DTOMapper<CodeSpace, CodeSpaceDTO> getMapper() {
		return mapper;
	}

	@Override
	protected Class<CodeSpace> getEntityClass() {
		return CodeSpace.class;
	}

	@Override
	protected DTOValidator<CodeSpace, CodeSpaceDTO> getValidator() {
		return validator;
	}
}
