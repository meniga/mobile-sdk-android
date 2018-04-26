package com.atlassian.oai.validator.mockwebserver;

import com.atlassian.oai.validator.model.Response;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nonnull;

import okhttp3.mockwebserver.MockResponse;
import okio.Buffer;

import static com.meniga.sdk.helpers.Objects.requireNonNull;

class MockWebServerResponse implements Response {

    private final MockResponse response;

    MockWebServerResponse(MockResponse response) {
        this.response = requireNonNull(response);
    }

    @Override
    public int getStatus() {
        return Integer.parseInt(response.getStatus().split(" ")[1]);
    }

    @Nonnull
    @Override
    public Optional<String> getBody() {
        return Optional.ofNullable(response.getBody()).map(new Function<Buffer, String>() {
            @Override
            public String apply(Buffer it) {
                return it.snapshot().utf8();
            }
        });
    }

    @Nonnull
    @Override
    public Collection<String> getHeaderValues(String name) {
        return response.getHeaders().values(name);
    }
}
