package maple.doljub.common.validation;

import jakarta.validation.GroupSequence;

import static maple.doljub.common.validation.ValidationGroups.*;

@GroupSequence({
        NotBlankGroup.class,
        PatternGroup.class,
        SizeGroup.class,
        EmailGroup.class
})
public interface ValidationSequence {
}
