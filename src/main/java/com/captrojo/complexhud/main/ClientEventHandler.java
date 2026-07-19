package com.captrojo.complexhud.main;

import com.captrojo.complexhud.api.PositionInfoXY2;
import com.captrojo.complexhud.position.PositionerBase;
import com.captrojo.complexhud.position.PositionerBottomCenter;
import com.captrojo.complexhud.position.PositionerBottomLeft;
import com.captrojo.complexhud.position.PositionerBottomRight;
import com.captrojo.complexhud.position.PositionerHotbarSideLeft;
import com.captrojo.complexhud.position.PositionerHotbarSideRight;
import com.captrojo.complexhud.position.PositionerMiddleCenter;
import com.captrojo.complexhud.position.PositionerMiddleLeft;
import com.captrojo.complexhud.position.PositionerMiddleRight;
import com.captrojo.complexhud.position.PositionerTopCenter;
import com.captrojo.complexhud.position.PositionerTopLeft;
import com.captrojo.complexhud.position.PositionerTopRight;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

public class ClientEventHandler
{
	static PositionerBase[] apos = new PositionerBase[] {
		new PositionerHotbarSideLeft(),
		new PositionerHotbarSideRight(),
		
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
	
	@SubscribeEvent
	public void onRenderGameOverlayPre(RenderGameOverlayEvent.Pre event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
	}

	@SubscribeEvent
	public void onRenderGameOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if (event.type != ElementType.ALL) { 
			return;
		}
		
		PositionerBase.reset(event.resolution);
		for (PositionerBase pb : apos) {
			if (pb != null) {
				pb.reset();
			}
		}
		
		for (RegisteredElement re : HUDElementList.element_list) {
			re.reloadValues1();
			if (!re.to_be_rendered) {
				continue;
			}
			re.reloadValues2();
			if (re.element.isFixed()) {
				re.pos = new PositionInfoXY2(0, re.width - 1, 0, re.height - 1);
				continue;
			}
			int i = re.element.getPosOrigin().ordinal();
			apos[i].addToPosCalc(re);
		}
		
		for (PositionerBase pb : apos) {
			if (pb != null) {
				pb.positionSections();
			}
		}
		
		for (RegisteredElement re : HUDElementList.element_list) {
			if (!re.to_be_rendered) {
				continue;
			}
			int i = re.element.getPosOrigin().ordinal();
			apos[i].positionElement(re);
			re.element.render(event.resolution, event.mouseX, event.mouseY, event.partialTicks, re.pos);
		}
	}
}
