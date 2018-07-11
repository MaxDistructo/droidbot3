package maxdistructo.discord.bots.droidbot.commands.server

import maxdistructo.discord.core.Roles
import sx.blah.discord.api.internal.json.objects.EmbedObject
import sx.blah.discord.handle.obj.IMessage
import sx.blah.discord.handle.obj.IRole
import sx.blah.discord.util.EmbedBuilder
import java.time.Instant

object NateBattleRolecards {
    fun onAsk(message : IMessage, enum : Enum<NateBattleRoles>) : EmbedObject{
        var builder = EmbedBuilder()
        lateinit var role : IRole
        when(enum){
            NateBattleRoles.PALLET ->{
                role = Roles.getRole(message, "Pallet Town")!!
                builder = simpleBuilder(role, "Pallet Town (Japanese: マサラタウン Masara Town) is located in western Kanto and serves as the hometown of Red, the protagonist of the Generation I games, as well as Blue and several other notable Pokémon Trainers who begin their Pokémon journeys in Kanto. Professor Oak's Laboratory is in Pallet Town, where the famous Pokémon Professor conducts his research. ", message)
            }
            NateBattleRoles.CELADON ->{
                role = Roles.getRole(message, "Celadon City")!!
                builder = simpleBuilder(role, "Celadon City (Japanese: タマムシシティ Tamamushi City) is located in central Kanto. It is the most populous city in Kanto and the eighth most populous in the Pokémon world, surpassing even Saffron City in the east.", message)
            }
            NateBattleRoles.CERULEAN ->{
                role = Roles.getRole(message, "Cerulean City")!!
                builder = simpleBuilder(role,"Cerulean City (Japanese: ハナダシティ Hanada City) is a seaside city located in northern Kanto. It is situated near a sea inlet to the north, with Saffron City to the south, and Mt. Moon to the west. It is home to Misty, the Cerulean City Gym Leader.", message)
            }
            NateBattleRoles.CINNABAR ->{
                role = Roles.getRole(message, "Cinnabar Island")!!
                builder = simpleBuilder(role, "Cinnabar Island (referred to in Japanese as both グレンじま Guren Island and グレンタウン Guren Town) is a large island located off the southern coast of the Kanto region, south of Pallet Town. It is home to a large volcano.", message)
            }
            NateBattleRoles.FUCHSIA ->{
                role = Roles.getRole(message, "Fuchsia City")!!
                builder = simpleBuilder(role, "Fuchsia City (Japanese: セキチクシティ Sekichiku City) is a city located in southwest Kanto. Its most distinguishing features are the Safari Zone in the Generation I and III games and the Poison-type Gym.", message)
            }
            NateBattleRoles.LAVENDER ->{
                role = Roles.getRole(message, "Lavender Town")!!
                builder = simpleBuilder(role, "Lavender Town (Japanese: シオンタウン Shion Town) is a small town located in northeast Kanto, just south of the Rock Tunnel. ", message)
            }
            NateBattleRoles.PEWTER ->{
                role = Roles.getRole(message, "Pewter City")!!
                builder = simpleBuilder(role, "Pewter City (Japanese: ニビシティ Nibi City) is a city located in northwest Kanto. The locale lies between Viridian Forest (via Route 2) and Mt. Moon (via Route 3).", message)
            }
            NateBattleRoles.SAFFRON ->{
                role = Roles.getRole(message, "Saffron City")!!
                builder = simpleBuilder(role, "Saffron City (Japanese: ヤマブキシティ Yamabuki City) is a sprawling metropolis in the Kanto region. It lies in between Celadon City, Vermilion City, Lavender Town, and Cerulean City.", message)
            }
            NateBattleRoles.VERMILION ->{
                role = Roles.getRole(message, "Vermilion City")!!
                builder = simpleBuilder(role, "Vermilion City (Japanese: クチバシティ Kuchiba City) is a city in Kanto." +
                        "Situated near a sea inlet to the south, it serves as a popular sea port for ships such as the S.S. Anne. Vermilion Harbor is a home port for many ships.", message)
            }
            NateBattleRoles.VIRIDIAN ->{
                role = Roles.getRole(message, "Viridian City")!!
                builder = simpleBuilder(role, "Viridian City (Japanese: トキワシティ Tokiwa City) is a small city located in western Kanto. In the Generation I and Generation III games, Team Rocket leader Giovanni serves as Leader of the Ground-specialist Viridian Gym, but the Gym is locked until the player has gained the other seven League Badges.", message)
            }
        }
        return builder.build()
    }
    private fun simpleBuilder(role : IRole, description : String,  message : IMessage) : EmbedBuilder{
        val name = role.name
        val color = role.color
        val builder = EmbedBuilder()
        val guild = message.guild
        builder.withAuthorName(name)
        builder.withColor(color)
        builder.withDescription(description)
        builder.withTimestamp(Instant.now())
        builder.withFooterIcon(guild.iconURL)
        builder.withFooterText(guild.name)
        return builder
    }
}