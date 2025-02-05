package com.mynewproject.core.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import lombok.Getter;

@Getter
@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProfileComponent {
    @ValueMapValue
    private String name;
    @ValueMapValue
    private String age;
    @ValueMapValue
    private String male;
    @ValueMapValue
    private String female;
    @ValueMapValue
    private String address;
    @Self
    private Resource resource;

    @ChildResource
    private List<ProfileComponentChild> actions = new ArrayList<>();    


}
