const UserListItem = ({ item }) => (
    <div>
        <div>
            ({item.playerId}) {item.username}
        </div>
        <div>{item.role}</div>
    </div>
);

export default UserListItem;
