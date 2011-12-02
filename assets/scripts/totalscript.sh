#!/system/bin/sh

remount()
{
	mount -o rw, remount -t ext4 /dev/block/mmcblk0p2 /data
	mount -o rw,remount -t yaffs2 /dev/block/mtdblock3 /system
}

change_dpi()
{
	if grep -q ro.sf.lcd_density /system/build.prop ; then
		sed -i 's/^.*ro.sf.lcd_density.*$/ro.sf.lcd_density='$2'/' /system/build.prop
	else
		echo "ro.sf.lcd_density=$2" >> /system/build.prop
	fi
}

clean()
{
rm /system/app/ADWLauncher.apk
rm /system/app/Androidian.apk
rm /system/app/BooksPhone.apk
rm /system/app/GenieWidget.apk
rm /system/app/Calculator.apk
rm /system/app/Calendar.apk
rm /system/app/CalendarProvider.apk
rm /system/app/CarHomeGoogle.apk
rm /system/app/CMWallpapers.apk
rm /system/app/Cyanbread.apk
rm /system/app/DeskClock.apk
rm /system/app/Development.apk
rm /system/app/DSPManager.apk
rm /system/app/Email.apk
rm /system/app/FileManager.apk
rm /system/app/FM.apk
rm /system/app/GoogleQuickSearchBox.apk
rm /system/app/HTMLViewer.apk
rm /system/app/Protips.apk
rm /system/app/SoundRecorder.apk
rm /system/app/SpareParts.apk
rm /system/app/Stk.apk
rm /system/app/VisualizationWallpapers.apk
rm /system/app/VoiceDialer.apk
rm /system/fonts/DroidSansArabic.ttf
rm /system/fonts/DroidSansFallback.ttf
rm /system/fonts/DroidSansHebrew.ttf
rm /system/fonts/DroidSansThai.ttf

rm /system/media/audio/alarms/Alarm_Beep_01.ogg
rm /system/media/audio/alarms/Alarm_Beep_02.ogg
rm /system/media/audio/alarms/Alarm_Buzzer.ogg
rm /system/media/audio/alarms/Alarm_Classic.ogg
rm /system/media/audio/alarms/Alarm_Rooster_02.ogg
rm /system/media/audio/notifications/Aldebaran.ogg
rm /system/media/audio/notifications/Altair.ogg
rm /system/media/audio/notifications/Antares.ogg
rm /system/media/audio/notifications/arcturus.ogg
rm /system/media/audio/notifications/Beat_Box_Android.ogg
rm /system/media/audio/notifications/Betelgeuse.ogg
rm /system/media/audio/notifications/CaffeineSnake.ogg
rm /system/media/audio/notifications/Canopus.ogg
rm /system/media/audio/notifications/Capella.ogg
rm /system/media/audio/notifications/Castor.ogg
rm /system/media/audio/notifications/CetiAlpha.ogg
rm /system/media/audio/notifications/Cricket.ogg
rm /system/media/audio/notifications/DearDeer.ogg
rm /system/media/audio/notifications/Drip.ogg
rm /system/media/audio/notifications/F1_MissedCall.ogg
rm /system/media/audio/notifications/F1_New_MMS.ogg
rm /system/media/audio/notifications/F1_New_SMS.ogg
rm /system/media/audio/notifications/Fomalhaut.ogg
rm /system/media/audio/notifications/Heaven.ogg
rm /system/media/audio/notifications/Highwire.ogg
rm /system/media/audio/notifications/KzurbSonar.ogg
rm /system/media/audio/notifications/Merope.ogg
rm /system/media/audio/notifications/moonbeam.ogg
rm /system/media/audio/notifications/pixiedust.ogg
rm /system/media/audio/notifications/pizzicato.ogg
rm /system/media/audio/notifications/Plastic_Pipe.ogg
rm /system/media/audio/notifications/Polaris.ogg
rm /system/media/audio/notifications/Pollux.ogg
rm /system/media/audio/notifications/Procyon.ogg
rm /system/media/audio/notifications/regulus.ogg
rm /system/media/audio/notifications/sirius.ogg
rm /system/media/audio/notifications/Sirrah.ogg
rm /system/media/audio/notifications/SpaceSeed.ogg
rm /system/media/audio/notifications/TaDa.ogg
rm /system/media/audio/notifications/Tinkerbell.ogg
rm /system/media/audio/notifications/tweeters.ogg
rm /system/media/audio/notifications/vega.ogg
rm /system/media/audio/notifications/Voila.ogg
rm /system/media/audio/ringtones/ANDROMEDA.ogg
rm /system/media/audio/ringtones/Aquila.ogg
rm /system/media/audio/ringtones/ArgoNavis.ogg
rm /system/media/audio/ringtones/BeatPlucker.ogg
rm /system/media/audio/ringtones/BentleyDubs.ogg
rm /system/media/audio/ringtones/Big_Easy.ogg
rm /system/media/audio/ringtones/BirdLoop.ogg
rm /system/media/audio/ringtones/Bollywood.ogg
rm /system/media/audio/ringtones/BOOTES.ogg
rm /system/media/audio/ringtones/BussaMove.ogg
rm /system/media/audio/ringtones/Cairo.ogg
rm /system/media/audio/ringtones/Calypso_Steel.ogg
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
rm /system/media/audio/ringtones/Eridani.ogg
rm /system/media/audio/ringtones/EtherShake.ogg
rm /system/media/audio/ringtones/FreeFlight.ogg
rm /system/media/audio/ringtones/FriendlyGhost.ogg
rm /system/media/audio/ringtones/Funk_Yall.ogg
rm /system/media/audio/ringtones/GameOverGuitar.ogg
rm /system/media/audio/ringtones/Gimme_Mo_Town.ogg
rm /system/media/audio/ringtones/Glacial_Groove.ogg
rm /system/media/audio/ringtones/Growl.ogg
rm /system/media/audio/ringtones/HalfwayHome.ogg
rm /system/media/audio/ringtones/hydra.ogg
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
rm /system/media/audio/ringtones/Noises1.ogg
rm /system/media/audio/ringtones/Noises2.ogg
rm /system/media/audio/ringtones/Noises3.ogg
rm /system/media/audio/ringtones/No_Limits.ogg
rm /system/media/audio/ringtones/OrganDub.ogg
rm /system/media/audio/ringtones/Orion.ogg
rm /system/media/audio/ringtones/Paradise_Island.ogg
rm /system/media/audio/ringtones/Pegasus.ogg
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
rm /system/media/audio/ringtones/Third_Eye.ogg
rm /system/media/audio/ringtones/Thunderfoot.ogg
rm /system/media/audio/ringtones/TwirlAway.ogg
rm /system/media/audio/ringtones/URSAMINOR.ogg
rm /system/media/audio/ringtones/VeryAlarmed.ogg
rm /system/media/audio/ringtones/Vespa.ogg
rm /system/media/audio/ringtones/World.ogg
rm /system/media/audio/ui/camera_click.ogg
rm /system/media/audio/ui/LowBattery.ogg

rm /data/local/logger.ko
} 

fix_perms()
{
fix_permissions
}

for i
do
  case "$i" in
	remount) remount;;
	dpi) change_dpi $2;;
	clean) clean;;
	fix_perms) fix_perms;;
  esac
done
