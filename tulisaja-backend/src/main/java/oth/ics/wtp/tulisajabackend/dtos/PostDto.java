package oth.ics.wtp.tulisajabackend.dtos;

import oth.ics.wtp.tulisajabackend.entities.User;

import java.time.Instant;

public record PostDto(long id,
                      String post,
                      User username,
                      Instant creationTime,
                      int likeCount
) {}
