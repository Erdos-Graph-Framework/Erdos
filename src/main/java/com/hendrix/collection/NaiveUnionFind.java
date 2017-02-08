package com.hendrix.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * a naive implementation of UnionFind
 * @param <T> the class type of the members of the sets, has to have an immutable hashCode
 *           while the operations take place.
 */
public class NaiveUnionFind<T extends ImmutableHashCode> implements IUnionFind<T>
{
    private ArrayList<HashSet<T>>  _sets       = null;
    private HashMap<T, HashSet<T>> _mapMembers = null;

    public NaiveUnionFind() {

        _sets       = new ArrayList<>();
        _mapMembers = new HashMap<>();
    }

    public ArrayList<HashSet<T>> getDisjointSets()
    {
        ArrayList<HashSet<T>> arrResult = new ArrayList<>();

        for (HashSet<T> set : _sets) {
            if(set.size() != 0)
                arrResult.add(set);
        }

        return arrResult;
    }

    public void UNION(T member1, T member2)
    {
        HashSet<T> setMember1 = validateGetMemberSet(member1);
        HashSet<T> setMember2 = validateGetMemberSet(member2);

        for (T t : setMember2) {
            _mapMembers.put(t, setMember1);
        }

        setMember1.addAll(setMember2);

        setMember2.clear();
    }

    public void MAKESET(T member)
    {
        if(_mapMembers.containsKey(member))
            return;

        HashSet<T> setNew = new HashSet<>();

        setNew.add(member);

        _mapMembers.put(member, setNew);

        _sets.add(setNew);
    }

    public HashSet<T> FINDSET(T member)
    {
        return validateGetMemberSet(member);
    }

    protected HashSet<T> validateGetMemberSet(T member)
    {
        HashSet<T> setMember = _mapMembers.get(member);

        if(setMember == null)
            throw new Error("UnionFind error:: member does not belong to any set");

        return setMember;
    }

}
