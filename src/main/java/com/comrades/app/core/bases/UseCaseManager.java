package com.comrades.app.core.bases;

public interface UseCaseManager {

    void prepare(UseCase usecase);

    void complete(UseCase usecase);

    void destroy(UseCase usecase);
}
