package com.example.callscreenapp.data

import android.util.Log
import com.example.callscreenapp.database.Avatar
import com.example.callscreenapp.database.Background
import com.example.callscreenapp.database.BackgroundTheme
import com.example.callscreenapp.database.ContactDb
import com.example.callscreenapp.model.ListAvatar
import com.example.callscreenapp.model.ListCallButton
import com.example.callscreenapp.model.PhoneCallListImage
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlin.random.Random

public val config = RealmConfiguration.create(schema = setOf(Avatar::class, BackgroundTheme::class, ContactDb::class, Background::class))
public val realm: Realm = Realm.open(config)



public const val CategoryAnimeSize: Int = 10
public const val CategoryLoveSize: Int = 10
public const val CategoryAnimalSize: Int = 10
public const val CategoryNatureSize: Int = 10
public const val CategoryGameSize: Int = 10
public const val CategoryCastleSize: Int = 10
public const val CategoryFantasySize: Int = 10
public const val CategoryTechSize: Int = 10
public const val CategorySeaSize: Int = 10

public const val ButtonCallSize: Int = 10

public const val AvatarSize: Int = 10

public const val HttpUrlBackground: String =
    "https://raw.githubusercontent.com/Pm2112/storage/main/Data%20Background/"
public const val AnimeUrl: String = "Anime/anime"
public const val LoveUrl: String = "Love/love"
public const val AnimalUrl: String = "Animal/animal"
public const val NatureUrl: String = "Nature/nature"
public const val GameUrl: String = "Game/game"
public const val CastleUrl: String = "Castle/castle"
public const val FantasyUrl: String = "Fantasy/fantasy"
public const val TechUrl: String = "Tech/tech"
public const val SeaUrl: String = "Sea/sea"

public const val HttpUrlButtonCall: String =
    "https://raw.githubusercontent.com/Pm2112/storage/main/Data%20Button/"

public const val HttpUrlAvatar: String =
    "https://raw.githubusercontent.com/Pm2112/storage/main/Data%20Avatar/"


val ListCategoryAll = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryAnimeSize) add(PhoneCallListImage("${HttpUrlBackground}${AnimeUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryLoveSize) add(PhoneCallListImage("${HttpUrlBackground}${LoveUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryAnimalSize) add(PhoneCallListImage("${HttpUrlBackground}${AnimalUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryNatureSize) add(PhoneCallListImage("${HttpUrlBackground}${NatureUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryGameSize) add(PhoneCallListImage("${HttpUrlBackground}${GameUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryCastleSize) add(PhoneCallListImage("${HttpUrlBackground}${CastleUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryFantasySize) add(PhoneCallListImage("${HttpUrlBackground}${FantasyUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategoryTechSize) add(PhoneCallListImage("${HttpUrlBackground}${TechUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
    for (i in 1..CategorySeaSize) add(PhoneCallListImage("${HttpUrlBackground}${SeaUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
//    shuffle(Random(System.currentTimeMillis()))
}

val ListCategoryAnime = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryAnimeSize) add(PhoneCallListImage("${HttpUrlBackground}${AnimeUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}


val ListCategoryLove = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryLoveSize) add(PhoneCallListImage("${HttpUrlBackground}${LoveUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryAnimal = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryAnimalSize) add(PhoneCallListImage("${HttpUrlBackground}${AnimalUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryNature = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryNatureSize) add(PhoneCallListImage("${HttpUrlBackground}${NatureUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryGame = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryGameSize) add(PhoneCallListImage("${HttpUrlBackground}${GameUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryCastle = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryCastleSize) add(PhoneCallListImage("${HttpUrlBackground}${CastleUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryFantasy = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryFantasySize) add(PhoneCallListImage("${HttpUrlBackground}${FantasyUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategoryTech = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategoryTechSize) add(PhoneCallListImage("${HttpUrlBackground}${TechUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}
val ListCategorySea = mutableListOf<PhoneCallListImage>().apply {
    for (i in 1..CategorySeaSize) add(PhoneCallListImage("${HttpUrlBackground}${SeaUrl}_$i.webp", "${HttpUrlAvatar}${i}.webp", "${HttpUrlButtonCall}${i}B.webp", "${HttpUrlButtonCall}${i}A.webp"))
}

val ListButtonCall = mutableListOf<ListCallButton>().apply {
    for (i in 1..ButtonCallSize) add(
        ListCallButton(
            "${HttpUrlButtonCall}${i}B.webp",
            "${HttpUrlButtonCall}${i}A.webp"
        )
    )
}

val items: RealmResults<Avatar> = realm.query<Avatar>().find()
val ListAvatarUrl = mutableListOf<String>().apply {
    for (avatar in items) {
        add(avatar.avatarUri)
    }
    for (i in 1..AvatarSize) add("${HttpUrlAvatar}${i}.webp")
}

val ListAvatar = mutableListOf<ListAvatar>().apply {
    for (item in ListAvatarUrl) {
        add(ListAvatar(item))
    }
}