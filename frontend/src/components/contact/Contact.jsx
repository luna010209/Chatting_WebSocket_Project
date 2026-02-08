import React, { useEffect, useRef, useState } from 'react'
import NavbarLogin from '../navbar/NavbarLogin'
import './Contact.css';
import { chatContent, receiver } from '../../services/contact/ContactService';
import { confirmMes, editMes, newMes } from '../../services/contact/MesService';
import { Client } from '@stomp/stompjs';
import { userLogin } from '../../services/user/UserService';
import ListContact from './ListContact';
import Message from './Message';

const Contact = () => {

    const userLoginId = useRef(null);
    const [receiverId, setReceiverId] = useState(null);
    const currentId = useRef(null);
    const [mesSend, setMesSend] = useState(null);
    const scrollableDivRef = useRef(null);
    const setScrollableDivRef = node => {
        if (node) {
            // Scroll to the bottom after the node is updated
            node.scrollTop = node.scrollHeight;
        }
        scrollableDivRef.current = node;
    };
    const token = localStorage.getItem('token');
    useEffect(() => {
        userLogin(token).then(res => {
            userLoginId.current = res.data.id;
        })
    }, [token])


    const client = useRef(new Client({
        brokerURL: "ws://localhost:3000/ws",
    }));

    useEffect(() => {
        client.current.activate();
        client.current.onConnect = (frame) => {
            // console.log(frame);
            client.current.subscribe('/topic/new', res => {
                const message = JSON.parse(res.body); // Parse the new message
                if ((message.receiverId === userLoginId.current && message.senderId === Number(currentId.current)) ||
                    (message.senderId === userLoginId.current && message.receiverId === Number(currentId.current))) {
                    setListMes(prevMessages => [...prevMessages, message]);
                }
            });

            client.current.subscribe('/topic/edit', res => {
                const message = JSON.parse(res.body);
                setListMes(prevMessages =>
                    prevMessages.map(msg =>
                        msg.id === message.id ? message : msg // Replace the old message with the updated one
                    )
                )

            })

            client.current.subscribe('/topic/confirm', (res) => {
                const message = JSON.parse(res.body);
                setListMes(prevMessages =>
                    prevMessages.map(msg =>
                        msg.id === message.id ? message : msg
                    )
                )
            })
            client.current.subscribe('/topic/remove', (res) => {
                const mesDeleteId = JSON.parse(res.body);
                setListMes(prevMessages =>
                    prevMessages.filter(msg =>
                        msg.id !== mesDeleteId
                    )
                )
            })
        }
    }, [])


    const [receiverAvatar, setAvatar] = useState('');
    const [receiverName, setName] = useState('');
    const [cover, setCover] = useState('');
    const [chatDis, setChatDis] = useState('d-none');
    const [listMes, setListMes] = useState([]);
    const [cancelEdit, setCancelEdit] = useState('d-none');

    useEffect(() => {
        if (receiverId !== null) {
            chatContent(token, receiverId).then(res => {
                setListMes(res.data);
                res.data.forEach(mes => {
                    if (mes.receiverId === Number(userLoginId.current) && !mes.confirm) {
                        confirmMes(client, { id: mes.id });
                    }
                })
            })
        }
    }, [receiverId, token])

    const chatBox = (id, e) => {
        e.preventDefault();
        setReceiverId(id);
        currentId.current = id;
        setCover('d-none');
        setChatDis('');
        receiver(token, id).then(res => {
            setAvatar(res.data.avatar);
            setName(res.data.name);
        })
    }

    const xmark = (e) => {
        e.preventDefault();
        setCover('');
        setChatDis('d-none');
        setReceiverId(null);
        currentId.current = null;
    }

    const [mesContent, setMesContent] = useState('');

    const formSendMes = (mesSend, e) => {
        e.preventDefault();
        if (!mesSend) {
            const requestDto = {
                content: mesContent,
                senderId: userLoginId.current,
                receiverId: receiverId,
            }
            newMes(client, requestDto);
            setMesContent('');
        } else {
            if (mesContent === mesSend.content) {
                alert("Your message does not change!!!");
                return;
            }
            editMes(client, { id: mesSend.id, content: mesContent });
            setMesSend(null);
            setMesContent('');
            setCancelEdit('d-none');
        }
        listMes.map(mes => {
            if (mes.receiverId === Number(userLoginId.current) && !mes.confirm) {
                confirmMes(client, { id: mes.id });
            }
        })
    }

    const mesEdit = (message, e) => {
        e.preventDefault();
        setCancelEdit('');
        setMesContent(message.content);
        setMesSend(message);
    }

    const cancelEditClick = (e) => {
        e.preventDefault();
        setCancelEdit('d-none');
        setMesContent('');
        setMesSend(null);
    }


    return (
        <div className="chat-page" style={{ fontFamily: "Nanum Myeongjo, serif" }}>
            <NavbarLogin />
            <div className="chat-layout">

                <ListContact
                    chatBox={chatBox}
                />

                <div className={`${cover} col-12 col-lg-9 d-flex align-items-center justify-content-center`}>
                    <img src="assets/contact/topic.png" className="img-fluid rounded-4" />
                </div>

                <div className={`chat-area ${chatDis}`}>
                    <div className="modern-chat">

                        {/* HEADER */}
                        <div className="modern-chat-header">
                            <div className="modern-user">
                                <img src={receiverAvatar} alt="" />
                                <div>
                                    <div className="modern-name">{receiverName}</div>
                                    <div className="modern-status">Active now</div>
                                </div>
                            </div>

                            <div className="modern-actions">
                                <i className="fa-solid fa-video" onClick={e => startCall(receiverId, e)}></i>
                                <i className="fa-solid fa-xmark" onClick={xmark}></i>
                            </div>
                        </div>

                        {/* MESSAGE AREA */}
                        <div className="modern-chat-body" ref={setScrollableDivRef}>
                            {listMes.map(message => {
                                if (message.status === "Message") {
                                    return (
                                        <Message
                                            key={message.id}
                                            message={message}
                                            receiverId={receiverId}
                                            mesEdit={mesEdit}
                                            client={client}
                                        />
                                    );
                                }

                                return null;
                            })}
                        </div>

                        {/* INPUT */}
                        <div className="modern-chat-input">
                            {mesSend && (
                                <div className="edit-indicator">
                                    Editing message...
                                    <span onClick={cancelEditClick}>Cancel</span>
                                </div>
                            )}

                            <form onSubmit={e => formSendMes(mesSend, e)}>
                                <input
                                    type="text"
                                    placeholder="Type a message..."
                                    value={mesContent}
                                    onChange={e => setMesContent(e.target.value)}
                                    required
                                />
                                <button type="submit">
                                    <i className="fa-solid fa-paper-plane"></i>
                                </button>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    )
}

export default Contact