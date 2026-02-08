import React, { useEffect, useState } from 'react'
import { listUser } from '../../services/contact/ContactService';
import { numOfMes } from '../../services/contact/MesService';
import './Contact.css';

const ListContact = ({ chatBox }) => {

    const token = localStorage.getItem('token');
    const [users, setUsers] = useState([]);
    useEffect(() => {
        listUser(token).then(res => {
            setUsers(res.data);
            res.data.forEach(sender => {
                numOfMes(token, sender.id).then(res => {
                    if (res.data !== 0) document.getElementById(`sender${sender.id}`).classList.remove('d-none');
                    document.getElementById(`sender${sender.id}`).innerText = res.data;
                })
            })
        })
    }, [token]);

    return (
        <div className="contact-sidebar d-none d-lg-flex">
            <div className="contact-title">Friends</div>

            <div className="contact-scroll">
                {users.map(user => (
                    <div key={user.id} className="contact-item"
                        onClick={e => chatBox(user.id, e)}>

                        <div style={{ display: 'flex', alignItems: 'center', gap: '10px' }}>
                            <img
                                src={user.avatar}
                                style={{
                                    width: "38px",
                                    height: "38px",
                                    borderRadius: "50%",
                                    objectFit: "cover"
                                }}
                            />
                            <span>{user.name}</span>
                        </div>

                        <div style={{ position: 'relative' }}>
                            <i className="fa-solid fa-comments"></i>
                            <span
                                id={`sender${user.id}`}
                                className="message-badge d-none"
                            />
                        </div>
                    </div>
                ))}
            </div>
        </div>
    )
}

export default ListContact