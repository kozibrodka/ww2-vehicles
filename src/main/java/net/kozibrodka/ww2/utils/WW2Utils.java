package net.kozibrodka.ww2.utils;

import net.minecraft.item.ItemStack;

public class WW2Utils {

    public static int countCharInRecipeList(String[] recipelist, char target) {
        int totalCount = 0;

        // 1. Sprawdź, czy tablica w ogóle istnieje
        if (recipelist == null) {
            return 0;
        }

        // 2. Przejdź pętlą przez każdy tekst (przepis) w tablicy
        for (String recipe : recipelist) {
            // 3. Zabezpiecz kod na wypadek, gdyby komórka tablicy była pusta (null)
            if (recipe != null) {
                // 4. Przejdź przez każdy znak w danym przepisie
                for (int i = 0; i < recipe.length(); i++) {
                    if (recipe.charAt(i) == target) {
                        totalCount++;
                    }
                }
            }
        }

        return totalCount;
    }


    public static String[] recipeCharList = new String[]{"Q", "W", "E", "A", "S", "D", "X", "C"};
    public static ItemStack[] engines = new ItemStack[4];
}
