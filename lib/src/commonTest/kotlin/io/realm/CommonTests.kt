package io.realm

import io.realm.model.Person
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

// Common tests will run on iOS device (./gradlew iosTest) or Android device (connectedAndroidTest)
// additional platfomr specifc test will also be included
class CommonTests {

    @Test
    fun createObject() {
        val configuration = RealmConfiguration.Builder()
            .name("createObject")
            .factory(TestUtils.factory())
            .build()

        val realm = Realm.open(configuration)

        realm.beginTransaction()
        val managedPerson = realm.create(Person::class)
        managedPerson.age = 2
        managedPerson.name = "Sophia"
        realm.commitTransaction()

        assertEquals(2, managedPerson.age)
        assertEquals("Sophia", managedPerson.name)
    }

    @Test
    fun queryObjects() {
        val configuration = RealmConfiguration.Builder()
            .name("queryObjects17")
            .factory(TestUtils.factory())
            .build()

        val realm = Realm.open(configuration)

        realm.beginTransaction()
        val managedPerson1 = realm.create(Person::class)
        val managedPerson2 = realm.create(Person::class)

        managedPerson1.name = "Foo"
        managedPerson1.age = 42

        managedPerson2.name = "FooBar"
        managedPerson2.age = 17
        realm.commitTransaction()

        val objects: RealmResults<Person> = realm.objects<Person>(Person::class, "name beginswith \"Foo\" SORT(name DESCENDING)")

        assertEquals(2, objects.size)
        val obj1 = objects[0]
        val obj2 = objects[1]

        assertEquals("Foo", obj1.name)
        assertEquals(42, obj1.age)
        assertEquals("FooBar", obj2.name)
        assertEquals(17, obj2.age)
    }

}