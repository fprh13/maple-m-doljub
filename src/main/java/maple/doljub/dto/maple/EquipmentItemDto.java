package maple.doljub.dto.maple;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentItemDto {
    @JsonProperty("item_equipment_page_name")
    private String equipmentPageName;

    @JsonProperty("item_equipment_slot_name")
    private String equipmentSlotName;

    @JsonProperty("item_name")
    private String itemName;
}
