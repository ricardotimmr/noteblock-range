package de.ricardo.noteblockrange.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import de.ricardo.noteblockrange.client.gui.NoteBlockRangeConfigScreen;

public final class ModMenuIntegration implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return NoteBlockRangeConfigScreen::new;
	}
}
