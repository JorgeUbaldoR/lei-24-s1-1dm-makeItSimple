# USEI24- Simulate Project Delays and their Impact

## 4. Tests

## Delay Class

### **Test : testUpdateActivityDuration**

```java
@Test
    void testUpdateActivityDuration() {
            // Positive duration increment
            delay.updateActivityDuration(activity1, 2.0);
            assertEquals(7.0, activity1.getDuration(), "Duration should increase by 2.0");

            // Negative duration increment
            delay.updateActivityDuration(activity1, -3.0);
            assertEquals(4.0, activity1.getDuration(), "Duration should decrease by 3.0");

            // Negative duration resulting in invalid state
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
        delay.updateActivityDuration(activity1, -10.0);
        });
        assertEquals("Duration cannot be negative", exception.getMessage());
        }
```

**Objective:** To verify that the duration was successfully updated

**Expected Result:** It should update the duration and catch exception

### **Test : testRemoveActivities**

```java
@Test
    void testRemoveActivities() {
            // Remove start and finish placeholders
            MapGraph<Activity, Double> updatedMap = delay.removeActivities(createdMap);

        // Ensure the placeholders are removed
        List<Activity> vertices = updatedMap.vertices();
        assertEquals(2, vertices.size(), "The updated map should only contain 2 activities");
        assertFalse(vertices.contains(new Activity(new ID(7777, TypeID.ACTIVITY), "Start Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>())), "Start placeholder should be removed");
        assertFalse(vertices.contains(new Activity(new ID(7778, TypeID.ACTIVITY), "Finish Placeholder", 0.0, "hours", 0.0, "USD", new ArrayList<>())), "Finish placeholder should be removed");

        // Ensure existing activities are still present
        assertTrue(vertices.contains(activity1), "Activity 1 should remain in the map");
        assertTrue(vertices.contains(activity2), "Activity 2 should remain in the map");
        }
```

**Objective:** To verify that the activities with ID: A-7777 and A-7778 are removed

**Expected Result:** A-7777 and A-7778 are removed

---
