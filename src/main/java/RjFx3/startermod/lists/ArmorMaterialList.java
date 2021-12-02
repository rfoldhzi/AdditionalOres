package RjFx3.startermod.lists;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum ArmorMaterialList implements IArmorMaterial{
	zyron("zyron",20,new int[] {2,3,4,1}, 25, ItemList.zyron_ingot, "item.armor.equip_generic", 1.0f, 0.0f),
	bronze("bronze",14,new int[] {4,5,7,2}, 6, ItemList.bronze_ingot, "item.armor.equip_generic", 0.0f, 0.0f),
	steel("steel",30,new int[] {2,5,6,2}, 5, ItemList.steel_ingot, "item.armor.equip_generic", 2.0f, 0.0f),
	spectral("spectral",30,new int[] {3,6,8,3}, 5, ItemList.zyron_ingot, "item.armor.equip_generic", 1.0f, 0.05f),
	tin("tin",3,new int[] {0,1,1,1}, 35, ItemList.tin_ingot, "item.armor.equip_generic", 0.0f, 0.2f);
	public static final int[] max_damage_array = new int[] {13,15,16,11};
	
	private String name, equipSound;
	private int durability, enchantability;
	private Item repairItem;
	private int[] damageReductionAmounts;
	private float toughness;
	private final float knockbackResistance;
	
	private ArmorMaterialList(String name, int durability, int[] damageReductionAmounts, int enchantability, Item repairItem, String equipSound, float toughness, float knockbackResistance) {
		this.name = name;
		this.equipSound = equipSound;
		this.durability = durability;
		this.enchantability = enchantability;
		this.repairItem = repairItem;
		this.damageReductionAmounts = damageReductionAmounts;
		this.toughness = toughness;
		this.knockbackResistance = knockbackResistance;
	}

	@Override
	public int getDamageReductionAmount(EquipmentSlotType slot) {
		// TODO Auto-generated method stub
		return this.damageReductionAmounts[slot.getIndex()];
	}

	@Override
	public int getDurability(EquipmentSlotType slot) {
		// TODO Auto-generated method stub
		return max_damage_array[slot.getIndex()] * this.durability;
	}

	@Override
	public int getEnchantability() {
		// TODO Auto-generated method stub
		return this.enchantability;
	}

	@Override
	public float getKnockbackResistance() {
		// TODO Auto-generated method stub
		return this.knockbackResistance;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "startermod:"+this.name;//This needs to be startermod.modid
	}

	@Override
	public Ingredient getRepairMaterial() {
		// TODO Auto-generated method stub
		return Ingredient.fromItems(this.repairItem);
	}

	@Override
	public SoundEvent getSoundEvent() {
		return new SoundEvent(new ResourceLocation(equipSound));
	}

	@Override
	public float getToughness() {
		// TODO Auto-generated method stub
		return this.toughness;
	}
}
