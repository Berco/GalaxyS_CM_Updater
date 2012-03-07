#!/system/bin/sh
# script for GalaxyS_CM_Updater
echo " **  SCRIPT FOR GALAXYS_CM_UPDATER ** "
prepare_runtime()
{
	echo " prepare_runtime"
	cd /datadata/in.deaap.genomen
	cat totalscript.sh > /cache/totalscript.sh
	chmod 777 /cache/totalscript.sh
}

prepare_recovery()
{
	echo "  prepare_recovery"
	cd /cache
	cat totalscript.sh > /tmp/totalscript.sh
	chmod 777 /tmp/totalscript.sh
}

remount()
{
	echo "  remount"
	mount -o rw, remount -t ext4 /dev/block/mmcblk0p2 /data
	mount -o rw,remount -t yaffs2 /dev/block/mtdblock2 /system
	#not usefull here, just here to remeber for runtime
}

wipe_dalvic()
{
	mount /data
    rm -rf /data/dalvik-cache
    umount /data
}

set_dpi()
{
	sed -i 's/^.*ro.sf.lcd_density.*$/ro.sf.lcd_density='$1'/' /system/build.prop
}

clean()
{
echo "  clean"
rm /system/app/Calculator.apk
rm /system/app/DeskClock.apk
rm /system/app/Development.apk
rm /system/app/DSPmanager.apk
rm /system/app/Email.apk
rm /system/app/Exchange.apk
rm /system/app/FileManager-1.1.6.apk
rm /system/app/Galaxy4.apk
rm /system/app/Gallery2.apk
rm /system/app/HoloSpiralWallpaper.apk
rm /system/app/LiveWallpapers.apk
rm /system/app/LiveWalpapersPicker.apk
rm /system/app/MagicSmokeWallpapers.apk
rm /system/app/NoiseField.apk
rm /system/app/PhaseBeam.apk
rm /system/app/QuickSearchBox.apk
rm /system/app/SoundRecorder.apk
rm /system/app/SpareParts.apk
#rm /system/app/Trebuchet.apk
rm /system/app/VideoEditor.apk
rm /system/app/VisualizationWallpapers.apk
rm /system/app/VoiceDialer.apk

rm /system/app/ChromeBookmarksSyncAdapter.apk
rm /system/app/GalleryGoogle.apk
rm /system/app/GenieWidget.apk
rm /system/app/GoogleQuickSearchBox.apk
rm /system/app/VoiceSearch.apk

rm /system/media/audio/alarms/*.*

rm /system/media/audio/notifications/Adara.ogg
rm /system/media/audio/notifications/Aldebaran.ogg
rm /system/media/audio/notifications/Altair.ogg
rm /system/media/audio/notifications/Antares.ogg
rm /system/media/audio/notifications/Antimony.ogg
rm /system/media/audio/notifications/Arcturus.ogg
rm /system/media/audio/notifications/Argon.ogg
rm /system/media/audio/notifications/Beat_Box_Android.ogg
rm /system/media/audio/notifications/Bellatrix.ogg
rm /system/media/audio/notifications/Beryllium.ogg
rm /system/media/audio/notifications/Betelgeuse.ogg
rm /system/media/audio/notifications/CaffeineSnake.ogg
rm /system/media/audio/notifications/Canopus.ogg
rm /system/media/audio/notifications/Capella.ogg
rm /system/media/audio/notifications/Castor.ogg
rm /system/media/audio/notifications/CetiAlpha.ogg
rm /system/media/audio/notifications/Cobalt.ogg
rm /system/media/audio/notifications/Cricket.ogg
rm /system/media/audio/notifications/DearDeer.ogg
rm /system/media/audio/notifications/Deneb.ogg
#rm /system/media/audio/notifications/Doink.ogg
rm /system/media/audio/notifications/DontPanic.ogg
rm /system/media/audio/notifications/Drip.ogg
rm /system/media/audio/notifications/Electra.ogg
rm /system/media/audio/notifications/F1_MissedCall.ogg
rm /system/media/audio/notifications/F1_New_MMS.ogg
rm /system/media/audio/notifications/F1_New_SMS.ogg
rm /system/media/audio/notifications/Fluorine.ogg
rm /system/media/audio/notifications/Fomalhaut.ogg
rm /system/media/audio/notifications/Gallium.ogg
rm /system/media/audio/notifications/Heaven.ogg
rm /system/media/audio/notifications/Helium.ogg
rm /system/media/audio/notifications/Highwire.ogg
rm /system/media/audio/notifications/Hojus.ogg
rm /system/media/audio/notifications/Iridium.ogg
rm /system/media/audio/notifications/Krypton.ogg
rm /system/media/audio/notifications/KzurbSonar.ogg
rm /system/media/audio/notifications/Lalande.ogg
rm /system/media/audio/notifications/Merope.ogg
rm /system/media/audio/notifications/Mira.ogg
rm /system/media/audio/notifications/moonbeam.ogg
rm /system/media/audio/notifications/OnTheHunt.ogg
rm /system/media/audio/notifications/Palladium.ogg
rm /system/media/audio/notifications/pixiedust.ogg
rm /system/media/audio/notifications/pizzicato.ogg
rm /system/media/audio/notifications/Plastic_Pipe.ogg
rm /system/media/audio/notifications/Polaris.ogg
rm /system/media/audio/notifications/Pollux.ogg
rm /system/media/audio/notifications/Procyon.ogg
rm /system/media/audio/notifications/Proxima.ogg
rm /system/media/audio/notifications/Radon.ogg
rm /system/media/audio/notifications/regulus.ogg
rm /system/media/audio/notifications/Rubidium.ogg
rm /system/media/audio/notifications/Selenium.ogg
rm /system/media/audio/notifications/Shaula.ogg
rm /system/media/audio/notifications/sirius.ogg
rm /system/media/audio/notifications/Sirrah.ogg
rm /system/media/audio/notifications/SpaceSeed.ogg
rm /system/media/audio/notifications/Spica.ogg
rm /system/media/audio/notifications/Strontium.ogg
rm /system/media/audio/notifications/TaDa.ogg
rm /system/media/audio/notifications/Tejat.ogg
rm /system/media/audio/notifications/Thallium.ogg
rm /system/media/audio/notifications/Tinkerbell.ogg
rm /system/media/audio/notifications/tweeters.ogg
rm /system/media/audio/notifications/Upsilon.ogg
rm /system/media/audio/notifications/Vega.ogg
rm /system/media/audio/notifications/Voila.ogg
rm /system/media/audio/notifications/Xenon.ogg
rm /system/media/audio/notifications/Zirconium.ogg

rm /system/media/audio/ringtones/Andromeda.ogg
rm /system/media/audio/ringtones/ANDROMEDA.ogg
rm /system/media/audio/ringtones/Aquila.ogg
rm /system/media/audio/ringtones/ArgoNavis.ogg
rm /system/media/audio/ringtones/Backroad.ogg
rm /system/media/audio/ringtones/BeatPlucker.ogg
rm /system/media/audio/ringtones/BentleyDubs.ogg
rm /system/media/audio/ringtones/Big_Easy.ogg
rm /system/media/audio/ringtones/BirdLoop.ogg
rm /system/media/audio/ringtones/Bollywood.ogg
rm /system/media/audio/ringtones/BOOTES.ogg
rm /system/media/audio/ringtones/BussaMove.ogg
rm /system/media/audio/ringtones/Cairo.ogg
rm /system/media/audio/ringtones/Calypso_Steel.ogg
rm /system/media/audio/ringtones/CanisMajor.ogg
rm /system/media/audio/ringtones/CANISMAJOR.ogg
rm /system/media/audio/ringtones/CaribbeanIce.ogg
rm /system/media/audio/ringtones/Carina.ogg
rm /system/media/audio/ringtones/CASSIOPEIA.ogg
rm /system/media/audio/ringtones/Centaurus.ogg
rm /system/media/audio/ringtones/Champagne_Edition.ogg
rm /system/media/audio/ringtones/Club_Cubano.ogg
rm /system/media/audio/ringtones/CrayonRock.ogg
rm /system/media/audio/ringtones/CrazyDream.ogg
rm /system/media/audio/ringtones/CurveBall.ogg
rm /system/media/audio/ringtones/Cygnus.ogg
rm /system/media/audio/ringtones/DancinFool.ogg
rm /system/media/audio/ringtones/Ding.ogg
rm /system/media/audio/ringtones/DonMessWivIt.ogg
rm /system/media/audio/ringtones/Draco.ogg
rm /system/media/audio/ringtones/DreamTheme.ogg
rm /system/media/audio/ringtones/Eastern_Sky.ogg
#rm /system/media/audio/ringtones/Enter_the_Nexus.ogg
rm /system/media/audio/ringtones/Eridani.ogg
rm /system/media/audio/ringtones/EtherShake.ogg
rm /system/media/audio/ringtones/FreeFlight.ogg
rm /system/media/audio/ringtones/FriendlyGhost.ogg
rm /system/media/audio/ringtones/Funk_Yall.ogg
rm /system/media/audio/ringtones/GameOverGuitar.ogg
rm /system/media/audio/ringtones/Gimme_Mo_Town.ogg
rm /system/media/audio/ringtones/Girtab.ogg
rm /system/media/audio/ringtones/Glacial_Groove.ogg
rm /system/media/audio/ringtones/Growl.ogg
rm /system/media/audio/ringtones/HalfwayHome.ogg
rm /system/media/audio/ringtones/Hydra.ogg
rm /system/media/audio/ringtones/InsertCoin.ogg
rm /system/media/audio/ringtones/LoopyLounge.ogg
rm /system/media/audio/ringtones/LoveFlute.ogg
rm /system/media/audio/ringtones/Lyra.ogg
rm /system/media/audio/ringtones/Machina.ogg
rm /system/media/audio/ringtones/MidEvilJaunt.ogg
rm /system/media/audio/ringtones/MildlyAlarming.ogg
rm /system/media/audio/ringtones/Nairobi.ogg
rm /system/media/audio/ringtones/Nassau.ogg
rm /system/media/audio/ringtones/NewPlayer.ogg
rm /system/media/audio/ringtones/No_Limits.ogg
rm /system/media/audio/ringtones/Noises1.ogg
rm /system/media/audio/ringtones/Noises2.ogg
rm /system/media/audio/ringtones/Noises3.ogg
rm /system/media/audio/ringtones/OrganDub.ogg
rm /system/media/audio/ringtones/Orion.ogg
rm /system/media/audio/ringtones/Paradise_Island.ogg
rm /system/media/audio/ringtones/Pegasus.ogg
rm /system/media/audio/ringtones/Perseus.ogg
rm /system/media/audio/ringtones/PERSEUS.ogg
rm /system/media/audio/ringtones/Playa.ogg
rm /system/media/audio/ringtones/Pyxis.ogg
rm /system/media/audio/ringtones/Revelation.ogg
rm /system/media/audio/ringtones/Rigel.ogg
rm /system/media/audio/ringtones/Ring_Classic_02.ogg
rm /system/media/audio/ringtones/Ring_Digital_02.ogg
rm /system/media/audio/ringtones/Ring_Synth_02.ogg
rm /system/media/audio/ringtones/Ring_Synth_04.ogg
rm /system/media/audio/ringtones/Road_Trip.ogg
rm /system/media/audio/ringtones/RomancingTheTone.ogg
rm /system/media/audio/ringtones/Safari.ogg
rm /system/media/audio/ringtones/Savannah.ogg
rm /system/media/audio/ringtones/Scarabaeus.ogg
rm /system/media/audio/ringtones/Sceptrum.ogg
rm /system/media/audio/ringtones/Seville.ogg
rm /system/media/audio/ringtones/Shes_All_That.ogg
rm /system/media/audio/ringtones/SilkyWay.ogg
rm /system/media/audio/ringtones/SitarVsSitar.ogg
rm /system/media/audio/ringtones/Solarium.ogg
rm /system/media/audio/ringtones/SpringyJalopy.ogg
rm /system/media/audio/ringtones/Steppin_Out.ogg
rm /system/media/audio/ringtones/Terminated.ogg
rm /system/media/audio/ringtones/Testudo.ogg
rm /system/media/audio/ringtones/Themos.ogg
rm /system/media/audio/ringtones/Third_Eye.ogg
rm /system/media/audio/ringtones/Thunderfoot.ogg
rm /system/media/audio/ringtones/TwirlAway.ogg
rm /system/media/audio/ringtones/UrsaMinor.ogg
rm /system/media/audio/ringtones/URSAMINOR.ogg
rm /system/media/audio/ringtones/VeryAlarmed.ogg
rm /system/media/audio/ringtones/Vespa.ogg
rm /system/media/audio/ringtones/World.ogg
rm /system/media/audio/ringtones/Zeta.ogg

rm /system/media/audio/ui/camera_click.ogg
rm /system/media/audio/ui/LowBattery.ogg
rm /system/media/audio/ui/VideoRecord.ogg
} 

fix_perms()
{
echo "  fix_perms"
fix_permissions
}

for i
do
  case "$i" in
	remount) remount;;
	set_dpi) set_dpi $2;;
	clean) clean;;
	wipe_dalvic) wipe_dalvic;;
	fix_perms) fix_perms;;
	prepare_runtime) prepare_runtime;;
	prepare_recovery) prepare_recovery;;
  esac
done
