package maxdistructo.discord.bots.droidbot.commands.mafia

import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IUser
import sx.blah.discord.util.EmbedBuilder
import java.time.Instant

object RoleCards {
    fun onRoleCardAsk(message: IMessage, role: String, user: IUser): EmbedObject {
        when (role) {
            "investigator" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A private eye who secretly gathers information.", false)
                builder.withAuthorName("Investigator")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/2/20/Achievement_Investigator.png/revision/latest/scale-to-width-down/50?cb=20140825150920")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/c/cb/Investigator.png/revision/latest?cb=20141222203926")
                builder.withDescription("Investigate one person each night for a clue to their role.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "sheriff" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "The law enforcer of the Town, forced into hiding from threat of murder.", false);
                builder.withAuthorName("Sheriff")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/e/e7/Achievement_Sheriff.png/revision/latest/scale-to-width-down/50?cb=20140825150706")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/3e/Sheriff.png/revision/latest/scale-to-width-down/150?cb=20140802032529")
                builder.withDescription("Interrogate one person each night for suspicious activity.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "spy" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A talented watcher who keeps track of evil in the Town.", false);
                builder.withAuthorName("Spy")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/0/0b/Achievement_Spy.png/revision/latest/scale-to-width-down/50?cb=20140825150715")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/36/Spy.png/revision/latest/scale-to-width-down/150?cb=20151129195522")
                builder.withDescription("You are able to read whispers and the chat of the mafia at night. You will also know who the mafia visits.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "lookout" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Investigative (TI)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "An eagle-eyed observer, stealthily camping outside houses to gain information.", false);
                builder.withAuthorName("Lookout")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/f/f6/Achievement_Lookout.png/revision/latest/scale-to-width-down/50?cb=20140825150631")
                //builder.withThumbnail("")
                builder.withDescription("Watch one person at night to see who visits them.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "vigilante" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 1 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A militant officer willing to bend the law to enact justice.", false);
                builder.withAuthorName("Vigilante")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/1/1f/Achievement_Vigilante.png/revision/latest?cb=20140825150808")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/3/3c/Brock_Smith.png/revision/latest/scale-to-width-down/138?cb=20160914033426")
                builder.withDescription("Choose to take justice into your own hands and shoot someone. You only have 3 shots and if you kill another town member, you will suicide.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "veteran" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 2 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A paranoid war hero who will shoot anyone who visits him.", false);
                builder.withAuthorName("Veteran")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/1/1b/Achievement_Veteran.png/revision/latest/scale-to-width-down/50?cb=20140825150759")
                //builder.withThumbnail("")
                builder.withDescription("Decide if you will go on alert. You can go on alert 3 times killing all who visit you. When you are on alert, you will have a defence of 1 or general immunity.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "vampire_hunter" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 1 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A priest turned monster hunter, this person slays Vampires.", false);
                builder.withAuthorName("Vampire Hunter")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8e/Achievement_Vampire_Hunter.png/revision/latest/scale-to-width-down/50?cb=20151130210939")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/c/c8/Vampire_Hunter.png/revision/latest/scale-to-width-down/150?cb=20151101133904")
                builder.withDescription("Check for Vampires each night. If you find a Vampire you will attack them. If a Vampire visits you, you will attack them. You can hear Vampires at night. If all the Vampires die you will become a Vigilante.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "jailor" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Killing (TK)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 2 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A prison guard who secretly detains suspects.", false)
                builder.withAuthorName("Jailor")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/6/64/Achievement_Jailor.png/revision/latest/scale-to-width-down/50?cb=20140825150602")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/7/7e/Jailor.png/revision/latest/scale-to-width-down/150?cb=20151021224315")
                builder.withDescription("You may choose one person during the day to jail for the night. You may anonymously talk with your prisoner. You can choose to attack your prisoner. The jailed target can't perform their night ability.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "bodyguard" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Protective (TP)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 2 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "An ex-soldier who secretly makes a living by selling protection.", false)
                builder.withAuthorName("Bodyguard")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/e/ef/Achievement_Bodyguard.png/revision/latest/scale-to-width-down/50?cb=20140708090613")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/8/87/Bodyguard.png/revision/latest?cb=20150207170237")
                builder.withDescription("Protect a player from direct attacks at night. If your target is attacked or is the victim of a harmful visit, you and the visitor will fight. If you successfully protect someone you can still be Healed.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "doctor" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Protective (TP)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A surgeon skilled in trauma care who secretly heals people.", false)
                builder.withAuthorName("Doctor")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/a/a0/Achievement_Doctor.png/revision/latest/scale-to-width-down/50?cb=20140708093156")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/6/6c/Doctor.png/revision/latest?cb=20140802032512")
                builder.withDescription("Heal one person each night, preventing them from dying. You may only Heal yourself once. You will know if your target is attacked.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "escort" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Support (TS)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A beautiful woman skilled in distraction.", false)
                builder.withAuthorName("Escort")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/2/29/Achievement_Escort.png/revision/latest/scale-to-width-down/50?cb=20140708093747")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/d/d3/Escort.png/revision/latest?cb=20150506224645")
                builder.withDescription("Distract someone each night. Distraction blocks your target from using their role's night ability. You cannot be role blocked.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "mayor" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Support (TS)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "The leader of the Town.", false)
                builder.withAuthorName("Mayor")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/a/aa/Achievement_Mayor_2017.png/revision/latest/scale-to-width-down/50?cb=20171130202502")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8b/MoneyBag.png/revision/latest?cb=20141029221203")
                builder.withDescription("You may reveal yourself as the Mayor of the Town. Once you have revealed yourself as Mayor your vote counts as 3 votes. You may not be healed once you have revealed yourself. Once revealed, you can't whisper or be whispered to.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "medium" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Support (TS)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A secret Psychic who talks with the dead.", false)
                builder.withAuthorName("Medium")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/6/63/Achievement_Medium.png/revision/latest/scale-to-width-down/50?cb=20140825150649")
                builder.withThumbnail("https://i.imgur.com/WBTx4Kx.png")
                builder.withDescription("You can speak to the dead and they can speak to you. All messages you send to the dead are anonymous. Once you die, you are able to tell the town one message.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "retributionist" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Support (TS)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A powerful mystic who will give one person a second chance at life.", false)
                builder.withAuthorName("Retributionist")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/6/61/Achievement_Retributionist.png/revision/latest/scale-to-width-down/50?cb=20140825150657")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/6/66/Nightspirit174%27s_Retributionist_Avatar.png/revision/latest?cb=20160423042204")
                builder.withDescription("You may revive 1 dead Town member.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "transporter" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Town Support (TS)", true)
                builder.appendField("Goal", "Lynch every criminal or evildoer", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, and other Town members", false)
                builder.appendField("Summary", "A man who transports people without asking any questions.", false)
                builder.withAuthorName("Transporter")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/5/5a/Achievement_Transporter.png/revision/latest/scale-to-width-down/50?cb=20140825150750")
                // builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/6/66/Nightspirit174%27s_Retributionist_Avatar.png/revision/latest?cb=20160423042204")
                builder.withDescription("Choose two people to transport at night. Transporting two people swaps all targets against them. You may transport yourself. Your targets will know they were transported. You may not transport someone with themselves. You can not transport Jailed people.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "blackmailer" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Support (MS)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "An eavesdropper who uses information to keep people quiet.", false)
                builder.withAuthorName("Blackmailer")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/0/0f/Achievement_Blackmailer.png/revision/latest?cb=20140825150350")
                builder.withThumbnail("https://orig00.deviantart.net/3959/f/2016/043/f/0/blackmailer_skin_5_by_purplellamas_town_of_salem_by_purplellamas5-d9ri8cf.png")
                builder.withDescription("Choose one person each night to blackmail. Blackmailed targets can not talk during the day. You can hear private messages.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "consigliere" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Support (MS)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A corrupted Investigator who gathers information for the Mafia.", false)
                builder.withAuthorName("Consigliere")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/f/f6/Achievement_Consigliere.png/revision/latest?cb=20140825150405")
                // builder.withThumbnail("https://orig00.deviantart.net/3959/f/2016/043/f/0/blackmailer_skin_5_by_purplellamas_town_of_salem_by_purplellamas5-d9ri8cf.png")
                builder.withDescription("Check one person for their exact role each night.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "consort" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Support (MS)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A beautiful dancer working for organized crime.", false)
                builder.withAuthorName("Consort")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/f/f4/Achievement_Consort.png/revision/latest/scale-to-width-down/50?cb=20140825150414")
                builder.withThumbnail("http://town-of-salem.wikia.com/wiki/File:Escort.png")
                builder.withDescription("Distract someone each night. Distraction blocks your target from using their role's night ability.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "disguiser" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Deception (MD)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A master of disguise who pretends to be other roles.", false)
                builder.withAuthorName("Disguiser")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/b/bf/Achievement_Disguiser.png/revision/latest/scale-to-width-down/50?cb=20140825150509")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/e/e4/NasubiNori_Disguiser_Avatar.png/revision/latest?cb=20180311074829")
                builder.withDescription("Choose a target to disguise yourself as. You will appear to be the role of your target to the Investigator. If you are killed you will appear to be the role of your target.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "forger" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Deception (MD)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A crooked lawyer that replaces documents.", false)
                builder.withAuthorName("Forger")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/f/f0/Achievement_Forger.png/revision/latest/scale-to-width-down/50?cb=20150801143107")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/6/66/ForgerAvatar.png/revision/latest?cb=20150724030632")
                builder.withDescription("If you chose to use your ability, the next person the mafia kills will get an improper response. You only have 3 uses of this role.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "framer" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Deception (MD)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A skilled counterfeiter who manipulates information.", false)
                builder.withAuthorName("Framer")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/5/5c/Achievement_Framer.png/revision/latest/scale-to-width-down/50?cb=20140825150526")
                //builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/6/66/ForgerAvatar.png/revision/latest?cb=20150724030632")
                builder.withDescription("Choose someone to frame at night. If your target is investigated they will appear to be a member of the Mafia.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "janitor" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Deception (MD)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A sanitation expert working for organized crime.", false)
                builder.withAuthorName("Janitor")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/1/1a/Achievement_Janitor.png/revision/latest/scale-to-width-down/50?cb=20140825150612")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/4/4d/Yuan_Itor.png/revision/latest/scale-to-width-down/154?cb=20160826042531")
                builder.withDescription("Choose a person to clean at night. If they die, their role will not be revealed to the town. You will know their role though. You only have 3 uses of this ability.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "godfather" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Killing (MK)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 1 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "The leader of organized crime.", false)
                builder.withAuthorName("Godfather")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/b/b1/Achievement_Godfather.png/revision/latest/scale-to-width-down/50?cb=20140825150541")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/9/97/Godfather_2.png/revision/latest/scale-to-width-down/100?cb=20160606042812")
                builder.withDescription("You decide who the mafia will kill. If you are role blocked, the Mafioso's kill will be used instead of your own. You are unable to be found by a Sheriff.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "mafioso" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Mafia Killing (MK)", true)
                builder.appendField("Goal", "Kill anyone who doesn't submit to Mafia.", true)
                builder.appendField("Attack: 1 - Defence: 0", "Your allies are Survivors, Pirates, Guardian Angels, Witches, and other Mafia Members.", false)
                builder.appendField("Summary", "A member of organized crime, trying to work their way to the top.", false)
                builder.withAuthorName("Mafioso")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/7/70/Achievement_Mafioso.png/revision/latest/scale-to-width-down/50?cb=20140825150640")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/7/70/DarkRevenant.png/revision/latest/scale-to-width-down/87?cb=20140701002425")
                builder.withDescription("You may choose a target to attack. If the Godfather chooses his own, then you will attack the Godfather's target instead. If the Godfather dies, you will be promoted to Godfather.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "amnesiac" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Benign (NB)", true)
                builder.appendField("Goal", "Remember a role and complete that role's goal.", true)
                builder.appendField("Attack: 0 - Defence: 0", "You have no allies until you remember a role.", false)
                builder.appendField("Summary", "A trauma patient that does not remember who he was.", false)
                builder.withAuthorName("Amnesiac")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/6/65/Achievement_Amnesiac.png/revision/latest/scale-to-width-down/50?cb=20140825150322")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/2/2f/Forgetful_Freddy.png/revision/latest/scale-to-width-down/110?cb=20160826030733")
                builder.withDescription("Remember who you were by selecting a dead person")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "survivor" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Benign (NB)", true)
                builder.appendField("Goal", "Survive to the end of the game.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Everyone is your ally if they let you survive.", false)
                builder.appendField("Summary", "A Neutral character who just wants to live.", false)
                builder.withAuthorName("Survivor")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/5/57/Achievement_Survivor.png/revision/latest/scale-to-width-down/50?cb=20140825150726")
                builder.withThumbnail("https://orig00.deviantart.net/8843/f/2016/008/a/1/survivor_avatar_for_tos_by_nasubinori-d9n6u8b.png")
                builder.withDescription("You may use a vest protecting yourself. Each of 4 vests can only be used once.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "executioner" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Evil (NE)", true)
                builder.appendField("Goal", "Get your target lynched", true)
                builder.appendField("Attack: 0 - Defence: 0", "Everyone can be your ally as long as your goal is completed.", false)
                builder.appendField("Summary", "An obsessed lyncher who will stop at nothing to execute his target.", false)
                builder.withAuthorName("Executioner")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/8/8c/Achievement_Executioner.png/revision/latest/scale-to-width-down/50?cb=20140825150517")
                //builder.withThumbnail("https://orig00.deviantart.net/8843/f/2016/008/a/1/survivor_avatar_for_tos_by_nasubinori-d9n6u8b.png")
                builder.withDescription("Trick the Town into lynching your target. If your target gets killed by anyone else, you will become a Jester.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "jester" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Evil (NE)", true)
                builder.appendField("Goal", "Get lynched by the town", true)
                builder.appendField("Attack: 0 - Defence: 0", "Everyone can be your ally as long as your goal is completed.", false)
                builder.appendField("Summary", "A crazed lunatic whose life goal is to be publicly executed.", false)
                builder.withAuthorName("Jester")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/0/05/Achievement_Jester.png/revision/latest/scale-to-width-down/50?cb=20140825150623")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/e/e0/Jester.png/revision/latest?cb=20140716035330")
                builder.withDescription("Trick the town into lynching you.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "witch" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Evil (NE)", true)
                builder.appendField("Goal", "Survive to see the Town lose the game.", true)
                builder.appendField("Attack: 0 - Defence: 0", "Everyone but the town can be your ally", false)
                builder.appendField("Summary", "A voodoo master who can control other people's actions.", false)
                builder.withAuthorName("Witch")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/2/20/Achievement_Witch.png/revision/latest/scale-to-width-down/50?cb=20140825150816")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/e/e6/Witch.png/revision/latest?cb=20140716035354")
                builder.withDescription("Control someone each night. You can force people to target themselves. Your victim will know they are being controlled. You will know the role of the player you control.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "arsonist" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Killing (NK)", true)
                builder.appendField("Goal", "See everyone burn.", true)
                builder.appendField("Attack: 3 - Defence: 2", "Survivor, Witch, Pirate, Guardian Angels, and other Arsonists are your allies.", false)
                builder.appendField("Summary", "A pyromaniac that wants to burn everyone.", false)
                builder.withAuthorName("Arsonist")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/c/cf/Achievement_Arsonist.png/revision/latest/scale-to-width-down/50?cb=20140825150335")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/c/c3/Arsonist.png/revision/latest?cb=20141029221117")
                builder.withDescription("You may Douse someone in gasoline or ignite Doused targets. If you are doused and do no action one night, you will clean off the gas from another Arsonist.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "serial_killer" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Killing (NK)", true)
                builder.appendField("Goal", "Kill everyone who would oppose you.", true)
                builder.appendField("Attack: 1 - Defence: 2", "Survivor, Witch, Pirate, Guardian Angels, and other Serial Killers are your allies.", false)
                builder.appendField("Summary", "A psychotic criminal who wants everyone to die.", false)
                builder.withAuthorName("Serial Killer")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/9/98/Achievement_Serial_Killer.png/revision/latest/scale-to-width-down/50?cb=20140723234035")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/7/75/SerialKiller.png/revision/latest?cb=20140816021322")
                builder.withDescription("You may kill a target every night. If someone tries to roleblock you, they will die. If you are Jailed and not executed, you will kill the Jailor.")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
            "werewolf" -> {
                val builder = EmbedBuilder()
                val guild = message.guild
                builder.appendField("Class", "Neutral Killing (NK)", true)
                builder.appendField("Goal", "Kill everyone who would oppose you.", true)
                builder.appendField("Attack: 3 - Defence: 2", "Survivor, Witch, Pirate, Guardian Angels, and other Werewolves are your allies.", false)
                builder.appendField("Summary", "A normal citizen who transforms during the full moon.", false)
                builder.withAuthorName("Werewolf")
                builder.withAuthorIcon("https://vignette.wikia.nocookie.net/town-of-salem/images/0/07/Achievement_Werewolf2.png/revision/latest?cb=20170730212305")
                builder.withThumbnail("https://vignette.wikia.nocookie.net/town-of-salem/images/c/c1/Lycanthrope.png/revision/latest/scale-to-width-down/151?cb=20160506214350")
                builder.withDescription("You will go on a rampage every other night. Going on a rampage means that all people who visit your target will die. If you do not chose a target, then all people who visit you will die")
                builder.withTimestamp(Instant.now())
                builder.withFooterIcon(guild.iconURL)
                builder.withFooterText(guild.name)
                builder.withColor(user.getColorForGuild(message.guild))
                return builder.build()
            }
        }
        val nulBuilder = EmbedBuilder()
        return nulBuilder.build()
    }
}