import React, { useEffect, useRef, useState } from 'react'
import { Client } from 'stompjs';
import { removeMes } from '../../services/contact/MesService';
import './Message.css';

const Message = ({ message, receiverId, mesEdit, client }) => {
    const isSender = message.receiverId === Number(receiverId);
    const mesRemove = (message, e) => {
        e.preventDefault();
        const deleteMes = confirm("Are you sure to delete this message?");
        if (deleteMes) {
            removeMes(client, { id: message.id });
            // document.getElementById(message.id).classList.add("d-none");
        }
    }
    return (
        <div
            key={message.id}
            id={`mes${message.id}`}
            className={`chat-message ${isSender ? 'sender' : 'receiver'}`}
        >
            <div className="bubble-wrapper">

                {isSender && (
                    <>
                        <button
                            onClick={e => mesEdit(message, e)}
                            className="icon-btn"
                        >
                            <i className="fa-solid fa-pen fa-xs"></i>
                        </button>

                        {!message.confirm && (
                            <button
                                onClick={e => mesRemove(message, e)}
                                className="icon-btn"
                            >
                                <i className="fa-solid fa-trash fa-xs"></i>
                            </button>
                        )}
                    </>
                )}

                <div className="chat-bubble">
                    {message.content}
                </div>
            </div>

            {message.edit && (
                <span className={`edited ${isSender ? 'text-end' : ''}`}>
                    (edited)
                </span>
            )}
        </div>
    )
}

export default Message