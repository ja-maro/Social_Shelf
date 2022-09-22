import { useEffect, useState } from "react";
import AddressListItem from "./AddressListItem";
import AddressService from "../services/address.service";
import { useNavigate } from "react-router-dom";


const AddressList = () => {

    const [addressList, setAddressList] = useState( [] );
    let navigate = useNavigate(); 

    useEffect(() => {
        if (!addressList.length) {
            AddressService.getAll().then((response) => {
                if (response.status === 200) {
                    console.log(response);
                    setAddressList(
                        response.data.sort((a, b) => a.user.user_id - b.user.user_id)
                    );
                } else if (response.status === 401) {

                }
            });
        }
    });

    return(
    <div className="wrapper">  
         <button onClick={() => navigate(`/createAddress/`)}>Add new address</button>
        <h1>Address list</h1>
        <ul>
            {addressList.map(address => (
                <AddressListItem key={address.id} address={address} />
            ))}
        </ul>
    </div>
)};

export default AddressList;