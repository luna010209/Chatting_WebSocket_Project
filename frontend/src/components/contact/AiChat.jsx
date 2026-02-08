import { removeMes } from '../../services/contact/MesService';

const Message = ({ message, receiverId, mesEdit, client }) => {
    const isSender = message.receiverId === Number(receiverId);
    const mesRemove = (message, e)=>{
        e.preventDefault();
        const deleteMes = confirm("Are you sure to delete this message?");
        if (deleteMes) {
            removeMes(client, {id: message.id});
            // document.getElementById(message.id).classList.add("d-none");
        }
    }
    return (
        <div key={message.id} id={`mes${message.id}`} className={`pb-2 d-flex flex-wrap align-items-center ${isSender ? 'ms-5 justify-content-end':'me-5'}`}>
            <div className={`w-100 d-flex align-items-center ${isSender ? 'justify-content-end':''}`}>
                <a onClick={e => { mesEdit(message, e) }} href="#" className={`${isSender? '':'d-none'} position-relative rounded p-1 bg-light me-1`}>
                    <i className="fa-solid fa-pen fa-sm"></i>
                </a>
                <a onClick={e => { mesRemove(message, e) }} href="#" className={`${isSender && !message.confirm? '':'d-none'} position-relative rounded p-1 bg-light me-1`}>
                    <i className="fa-solid fa-trash fa-sm"></i>
                </a>
                <div className={`p-2 rounded ${isSender? 'text-bg-info':'bg-body d-inline border'}`}>
                    {message.content}
                </div>
            </div>
            <span className={`${message.edit ? '' : 'd-none'} w-100 text-secondary ${isSender? 'text-end':''}`} style={{fontSize: "small"}}>
                (edited)
            </span>
        </div>
    )
}

export default Message