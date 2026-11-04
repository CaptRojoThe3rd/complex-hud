package com.captrojo.complexhud.position;

import java.util.List;

import com.captrojo.complexhud.api.PositionInfoXY2;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.profiler.Profiler;

public abstract class PositioningHandler
{
	static PositionerBase[] apos = new PositionerBase[] {
		new PositionerHotbarSideLeft(),
		new PositionerHotbarSideRight(),

		new PositionerHotbarTopBoth(),
		new PositionerHotbarTopLeft(),
		new PositionerHotbarTopRight(),

		new PositionerTopLeft(),
		new PositionerTopCenter(),
		new PositionerTopRight(),

		new PositionerMiddleLeft(),
		new PositionerMiddleCenter(),
		new PositionerMiddleRight(),

		new PositionerBottomLeft(),
		new PositionerBottomCenter(),
		new PositionerBottomRight()
	};

	/* This function expects that the list provided is already sorted. */
	public static void positionElements(List<PositionedObject> obj_list, ScaledResolution sr)
	{
		Profiler pf = Minecraft.getMinecraft().mcProfiler;
		pf.startSection("positioning");

		/* Reset positioners */
		PositionerBase.reset(sr);
		for (PositionerBase pb : apos) {
			if (pb != null) {
				pb.reset();
			}
		}

		/* Ask each object how wide and tall it is */
		for (PositionedObject obj : obj_list) {
			obj.reloadValues1();
			if (!obj.to_be_rendered) {
				continue;
			}
			obj.reloadValues2();
			if (obj.isFixed()) {
				if (obj.pos == null) {
					obj.pos = new PositionInfoXY2();
				}
				obj.pos.set(0, obj.adj_width, 0, obj.adj_height);
				continue;
			}
			int i = obj.getPosOrigin().ordinal();
			apos[i].addToPosCalc(obj);
		}

		/* Position each section */
		for (PositionerBase pb : apos) {
			if (pb != null) {
				pb.positionSections();
			}
		}

		/* Tell each object where it is to be rendered */
		for (PositionedObject obj : obj_list) {
			if (!obj.to_be_rendered) {
				continue;
			}
			int i = obj.getPosOrigin().ordinal();
			apos[i].positionElement(obj);
		}

		pf.endSection();
	}
}
