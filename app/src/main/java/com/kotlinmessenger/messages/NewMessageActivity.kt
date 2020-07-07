package com.kotlinmessenger.messages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kotlinmessenger.R
import com.kotlinmessenger.models.User
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)

        supportActionBar?.title = "Select User"

        // Might need other ViewHolder
//        val adapter = GroupAdapter<GroupieViewHolder>()
//
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//        adapter.add(UserItem())
//
//        recyclerview_newmessage.adapter = adapter

        fetchUsers()

    }

    companion object {
        val USER_KEY = "USER_KEY"
    }

    private fun fetchUsers() {
        val ref = FirebaseDatabase.getInstance().getReference("/users")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()

                snapshot.children.forEach{
                    Log.d("NewMessage", it.toString())

                    val user = it.getValue(User::class.java)
                    if (user != null) {
                        adapter.add(UserItem(user))
                    }
                }

                // Item - row with name and picture ~
                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)

                    // allows to put data, and retrieve in other activity
                    intent.putExtra(USER_KEY, userItem.user)

                    startActivity(intent)

                    finish()
                }

                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })


    }
}

class UserItem(val user: User): Item<GroupieViewHolder>() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.username_textview_new_message.text = user.username

        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageview_new_message)
    }

    override fun getLayout(): Int {
        return R.layout.user_row_new_message
    }
}

