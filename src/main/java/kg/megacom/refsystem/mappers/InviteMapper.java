package kg.megacom.refsystem.mappers;

import kg.megacom.refsystem.models.Invite;
import kg.megacom.refsystem.models.dto.InviteDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InviteMapper {
    InviteMapper INSTANCE = Mappers.getMapper(InviteMapper.class);

    Invite toInvite(InviteDto inviteDto);
    InviteDto toInviteDto(Invite invite);
}
