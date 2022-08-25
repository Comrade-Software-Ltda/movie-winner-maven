package com.comrades.app.core.bases;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, LazyValidation.class})
public interface UseCaseValidationOrder {

}
