package com.github.dreamhead.moco.parser.model;

import com.github.dreamhead.moco.mount.MountPredicate;
import com.google.common.base.Function;

import java.util.List;

import static com.github.dreamhead.moco.MocoMount.exclude;
import static com.github.dreamhead.moco.MocoMount.include;
import static com.google.common.collect.ImmutableList.of;
import static com.google.common.collect.Iterables.*;

public class MountSetting {
    private String dir;
    private String uri;
    private List<String> includes = of();
    private List<String> excludes = of();

    public String getDir() {
        return dir;
    }

    public String getUri() {
        return uri;
    }

    public List<String> getIncludes() {
        return includes;
    }

    public List<String> getExcludes() {
        return excludes;
    }

    public MountPredicate[] getMountPredicates() {
        return toArray(toMountPredicates(), MountPredicate.class);
    }

    private Iterable<MountPredicate> toMountPredicates() {
        return unmodifiableIterable(concat(
                transform(includes, toInclude()),
                transform(excludes, toExclude())));
    }

    private Function<String, MountPredicate> toInclude() {
        return new Function<String, MountPredicate>() {
            @Override
            public MountPredicate apply(String input) {
                return include(input);
            }
        };
    }

    private Function<String, MountPredicate> toExclude() {
        return new Function<String, MountPredicate>() {
            @Override
            public MountPredicate apply(String input) {
                return exclude(input);
            }
        };
    }
}
