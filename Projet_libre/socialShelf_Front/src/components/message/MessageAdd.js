import { useEffect, useState } from "react";
import MessageService from "../../services/message.service";
import { Alert } from "@mui/material";

const MessageAdd = ({ event, refresh }) => {
    const [content, setContent] = useState("");
    const [messageEvent, setMessageEvent] = useState({});
    const [isAlert, setIsAlert] = useState(false);
    const [alertMessage, setAlertMessage] = useState({
        severity: "",
        message: "",
        status: "",
    });

    useEffect(() => {
        setMessageEvent(event);
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        let response;
        console.log(event.id);
        await MessageService.add(content, messageEvent.id).then((res) => (response = res));
        if (response.status === 201) {
            setIsAlert(true);
            setAlertMessage({
                severity: "success",
                message: "Sent",
                status: "201",
            });
            refresh();
        } else if (response.status === 403) {
            setIsAlert(true);
            setAlertMessage({
                severity: "error",
                message: "You don't have permission to do that",
                status: "403",
            });
        } else {
            setIsAlert(true);
            setAlertMessage({
                severity: "error",
                message: "Error",
                status: "",
            });
        }
    };

    const form = (
        <form onSubmit={handleSubmit}>
            <h3 className="title"> Send a Message </h3>
            <label>
                Content :
                <input
                    type="text"
                    name="content"
                    placeholder="your message here ..."
                    required
                    onChange={(event) => setContent(event.target.value)}
                />
            </label>
            <input type="submit" value="Send" />
        </form>
    );

    if (!isAlert) {
        return <div>{form}</div>;
    } else {
        return (
            <div>
                {form}
                <Alert
                    className="alert"
                    variant="filled"
                    severity={alertMessage.severity}
                >
                    {alertMessage.message}
                </Alert>
            </div>
        );
    }
};

export default MessageAdd;
