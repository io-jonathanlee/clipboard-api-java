package io.jonathanlee.clipboardapijava.dto.response.organization;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationDto {

  private String id;

  private String name;

  private Collection<String> memberEmails;

  private Collection<String> administratorEmails;

}
