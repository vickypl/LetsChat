/**
 * 
 */
package com.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



import java.util.HashSet;

import com.chat.db.DatabaseConnector;

/**
	author: Vicky pl
	email: vicky542011@gmail.com
	mobile: 7828789845
 **/
/**
 * @author Anonymox
 *
 */
public class UserController {

	public static HashSet<User> activeSet = new HashSet<User>();
	
	//for error log
	String servletname = this.getClass().getName();
	
	//for db connection
	DatabaseConnector db = new DatabaseConnector();

	
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement pstat = null;
	ResultSet resultSet = null;
	
	public ArrayList<User> getUserList(String sql) {
		ArrayList<User> list = new ArrayList<User>();
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			User user = null;
			while(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getString(1));
				user.setFirstname(resultSet.getString(2));
				user.setLastname(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setUsername(resultSet.getString(5));
				user.setPassword(resultSet.getString(6));
				user.setLastlogin(resultSet.getString(7));
				user.setRegdate(resultSet.getString(8));
				user.setUrl(resultSet.getString(9));
				user.setRole(resultSet.getString(10));
				list.add(user);
			}
			
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	//returns singel user
	public User getUser(String sql) {
		User user = null;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()) {
				user = new User();
				user.setId(resultSet.getString(1));
				user.setFirstname(resultSet.getString(2));
				user.setLastname(resultSet.getString(3));
				user.setEmail(resultSet.getString(4));
				user.setUsername(resultSet.getString(5));
				user.setPassword(resultSet.getString(6));
				user.setLastlogin(resultSet.getString(7));
				user.setRegdate(resultSet.getString(8));
				user.setUrl(resultSet.getString(9));
				user.setRole(resultSet.getString(10));
			}
			
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return user;
	}
	
	public boolean dpRecordUpdate(String sql) {
		boolean status = false;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public boolean updateLastLogin(String username) {
		boolean status = false;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			String sql="update client set lastlogin=SYSDATE where username='"+username+"'";
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	//returns list of all the connection id of given id
	public ArrayList<String> getConnectionIds(String fromid) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			String sql="select CONNECTION_TO_ID from connection where CONNECTION_FROM_ID='"+fromid+"'";
			resultSet=statement.executeQuery(sql);
			
			while(resultSet.next()) {
				list.add(resultSet.getString(1));
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	public ArrayList<User> getFriendList(String id) {
		
		ArrayList<String> friendsId = null;
		friendsId =	getConnectionIds(id);
		
		ArrayList<User> friendList = null;
		
		if(friendsId!=null) {
			friendList=new ArrayList<User>();
			for(String fid : friendsId) {
				String sql="select * from client where id='"+fid+"'";
				User usr = getUser(sql);
				friendList.add(usr);
			}
		}
		return friendList;
	}
	
	public ArrayList<Messages> getMessages(String sql) {
		ArrayList<Messages> list = new ArrayList<Messages>();
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			Messages msg = null;
			while(resultSet.next()) {
				msg = new Messages();
				msg.setId(resultSet.getString(1));
				msg.setFromId(resultSet.getString(2));
				msg.setSenderName(resultSet.getString(3));
				msg.setToId(resultSet.getString(4));
				msg.setMessage(resultSet.getString(5));
				msg.setTime(resultSet.getString(6));
				list.add(msg);
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	public ArrayList<Groups> getGroups(String sql) {
		ArrayList<Groups> list = new ArrayList<Groups>();
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			Groups grp = null;
			while(resultSet.next()) {
				grp = new Groups();
				grp.setId(resultSet.getString(1));
				grp.setCreatorId(resultSet.getString(2));
				grp.setGroupName(resultSet.getString(3));
				grp.setCreatedOn(resultSet.getString(4));
				list.add(grp);
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	public Groups getSingleGroups(String sql) {
		Groups grp = null;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()) {
				grp = new Groups();
				grp.setId(resultSet.getString(1));
				grp.setCreatorId(resultSet.getString(2));
				grp.setGroupName(resultSet.getString(3));
				grp.setCreatedOn(resultSet.getString(4));
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return grp;
	}
	
	public int getletestGroupId() {
		int id=-1;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select MAX(id) from groups");
			
			if(resultSet.next()) {
				id=resultSet.getInt(1);
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return id;
	}
	
	public boolean createGroup(int creatorId, String groupname) {
		boolean status = false;
		try {
			connection = db.connectTo();
			String sql="insert into groups (id, creatorid, groupname, createdon) values (group_id.nextval, ?, ?, SYSDATE)";
			pstat = connection.prepareStatement(sql);
			pstat.setInt(1, creatorId);
			pstat.setString(2, groupname);
			int r = pstat.executeUpdate();
			if(r>0) {
				status= true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public boolean deleteGroup(String id, String creatorId) {
		boolean status = false;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			String sql="delete groups where id='"+id+"' and creatorid='"+creatorId+"'";
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}

	public boolean addToGroup(int grpId, int frndId) {
		boolean status = false;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			String sql="insert into group_members (id, groupid, memberid) values (group_mem_id.nextval, "+grpId+", "+frndId+")";
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}

	public ArrayList<Groups> getGroupListUserParticipatedIn(String userId) {
		ArrayList<Groups> groupList = new ArrayList<Groups>();
		try {
			connection = db.connectTo();
			String sql="select * from groups g inner join group_members gm on gm.groupid=g.id and memberid='"+userId+"'";
			statement=connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				Groups grp = new Groups();
				grp.setId(resultSet.getString(1));
				grp.setCreatorId(resultSet.getString(2));
				grp.setGroupName(resultSet.getString(3));
				grp.setCreatedOn(resultSet.getString(4));
				groupList.add(grp);
			}
			
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return groupList;
	}
	
	public boolean sendGroupMessage(int grpId, String username, String msg) {
		boolean status = false;
		try {
			connection = db.connectTo();
			String sql="insert into group_msgs(id, groupid, sendername, message, senton) values(group_msg_id.nextval, '"+grpId+"', '"+username+"', '"+msg+"', SYSDATE)";
			statement=connection.createStatement();
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public boolean deleteGroupMsgs(String grpid) {
		boolean status = false;
		try {
			connection = db.connectTo();
			String sql="delete from group_msgs where groupid='"+grpid+"'";
			statement=connection.createStatement();
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public boolean deleteGroupMember(String grpid) {
		boolean status = false;
		try {
			connection = db.connectTo();
			String sql="delete from group_members where GROUPID='"+grpid+"'";
			statement=connection.createStatement();
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	
	//this takes out if the member is already presnet in the group or not
	public GroupMemberRelation getGroupMemberList(String sql) {
		GroupMemberRelation grpmemrel = null;
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			if(resultSet.next()) {
				grpmemrel = new GroupMemberRelation();
				grpmemrel.setId(resultSet.getString(1));
				grpmemrel.setGrpId(resultSet.getString(2));
				grpmemrel.setMemberId(resultSet.getString(3));
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return grpmemrel;
	}
	
	public boolean removeMemberFromGroup(String grpid, String memberId) {
		boolean status = false;
		try {
			connection = db.connectTo();
			String sql="delete from group_members where GROUPID='"+grpid+"' and MEMBERID='"+memberId+"'";
			statement=connection.createStatement();
			
			int result = statement.executeUpdate(sql);
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public ArrayList<GroupMemberRelation> getGroupMemberRelationList(String sql) {
		ArrayList<GroupMemberRelation> list = new ArrayList<GroupMemberRelation>();
		
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			GroupMemberRelation grpmemrel = null;
			while(resultSet.next()) {
				grpmemrel = new GroupMemberRelation();
				grpmemrel.setId(resultSet.getString(1));
				grpmemrel.setGrpId(resultSet.getString(2));
				grpmemrel.setMemberId(resultSet.getString(3));
				list.add(grpmemrel);
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	public ArrayList<GroupMessages> getGroupMessages(String sql) {
		ArrayList<GroupMessages> list = new ArrayList<GroupMessages>();
		try {
			connection = db.connectTo();
			statement=connection.createStatement();
			resultSet=statement.executeQuery(sql);
			
			GroupMessages grpmsg = null;
			while(resultSet.next()) {
				grpmsg = new GroupMessages();
				grpmsg.setId(resultSet.getString(1));
				grpmsg.setGroupId(resultSet.getString(2));
				grpmsg.setSenderName(resultSet.getString(3));
				grpmsg.setMessage(resultSet.getString(4));
				grpmsg.setSendOn(resultSet.getString(5));
				list.add(grpmsg);
			}
		
		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return list;
	}
	
	//gives list of members of perticular group
	public ArrayList<User> getGroupMembers(String grpId) {
		ArrayList<User> list = new ArrayList<User>();
		String sql="select * from GROUP_MEMBERS where groupid='"+grpId+"'";
		ArrayList<GroupMemberRelation> memberIds = getGroupMemberRelationList(sql);
		for(GroupMemberRelation gmr : memberIds) {
			sql="select * from client where id='"+gmr.getMemberId()+"'";
			User member = new User();
			member = getUser(sql);
			list.add(member);
		}	
		return list;
	}
	
	public boolean sendMessage(int from, String senderName, int to, String msg) {
		boolean status=false;
		try {
			connection = db.connectTo();
			String sumbitMsg="insert into user_messages (id, fromid, sendername, toid, message, timming) values (message_id.nextval, ?, ?, ?, ?, SYSDATE)";
			pstat = connection.prepareStatement(sumbitMsg);
			pstat.setInt(1, from);
			pstat.setString(2, senderName);
			pstat.setInt(3, to);
			pstat.setString(4, msg);
			
			int result = pstat.executeUpdate();
			if(result>0) {
				status=true;
			}

		} catch (SQLException e) {
			db.logToFile(e, servletname);
		} catch (Exception e) {
			db.logToFile(e, servletname);
		} finally {
			if(connection!=null) {
				try {
					connection.close();
				} catch(SQLException e) {
					db.logToFile(e, servletname);					
				}
			}
		}
		return status;
	}
	
	public void setUserToActiveSet(User user) {
		activeSet.add(user);
	}
	public int getActiveSetSize() {
		return activeSet.size();
	}
	public void removeFromActiveSet(User user) {
		activeSet.remove(user);
	}
	public HashSet<User> getStaticActiveSet() {
		return activeSet;
	}
}
