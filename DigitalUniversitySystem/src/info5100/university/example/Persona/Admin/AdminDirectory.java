/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package info5100.university.example.Persona.Admin;

import info5100.university.example.Persona.Admin.AdminProfile;
import info5100.university.example.Persona.Person;
import java.util.ArrayList;

/**
 *
 * @author chethan
 */
public class AdminDirectory {
    
    ArrayList<AdminProfile> adminlist;
    
    public AdminDirectory() {
        adminlist = new ArrayList();
    }
    
    public AdminProfile newAdminProfile(Person p) {
        AdminProfile ap = new AdminProfile(p);
        adminlist.add(ap);
        return ap;
    }
    
    public AdminProfile findAdmin(String id) {
        for (AdminProfile ap : adminlist) {
            if (ap.isMatch(id)) {
                return ap;
            }
        }
        return null;
    }
    
    public ArrayList<AdminProfile> getAdminList() {
        return adminlist;
    }
}
