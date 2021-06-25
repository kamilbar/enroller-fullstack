<template>
  <div>
    <new-meeting-form @added="addNewMeeting($event)"></new-meeting-form>
    <span v-if="meetings.length == 0">
               Brak zaplanowanych spotkań.
           </span>
    <h3 v-else>
      Zaplanowane zajęcia ({{ meetings.length }})
    </h3>

    <meetings-list :meetings="meetings"
                   :username="username"
                   @attend="addMeetingParticipant($event)"
                   @unattend="removeMeetingParticipant($event)"
                   @delete="deleteMeeting($event)"></meetings-list>

<!--    <span v-if="loadedMeetings.length == 0">
                   Brak spotkań.
               </span>
        <h3 v-else>
          Zajęcia ({{ loadedMeetings.length }})
        </h3>

        <meetings-list :meetings="loadedMeetings"
                       :username="username"
                       @attend="addMeetingParticipant($event)"
                       @unattend="removeMeetingParticipant($event)"
                       @delete="deleteMeeting($event)"></meetings-list>
-->
  </div>
</template>

<script>
    import NewMeetingForm from "./NewMeetingForm";
    import MeetingsList from "./MeetingsList";

    export default {
        components: {NewMeetingForm, MeetingsList},
        props: ['username'],
        data() {
            return {
                meetings: [],
//                loadedMeetings: []
            };
        },
        methods: {
            addNewMeeting(meeting) {
                    this.$http.post('meetings', meeting)
                        .then(response => {
                        this.meetings.push(meeting);
                    })
                   // .catch(response => this.failure('Błąd przy zakładaniu konta. Kod odpowiedzi: ' + response.status));
            },
            addMeetingParticipant(meeting) {
                this.$http.post(`meetings/${meeting.id}`, this.username)
 //                                          console.log(meeting.id)
 //                                            console.log(this.username)
                    .then(response => {
                    meeting.participants.push(this.username);
                    this.meetings.push(meeting)
   //                     meeting.participants.push(this.username);
                })
                .catch(response => console.log(response.status));
            },
            removeMeetingParticipant(meeting) {
                this.$http.post(`meetings/${meeting.id}/removeParticipant_{participant.id}`)
                    .then(response => {
                    meeting.participants.splice(meeting.participants.indexOf(participant.id), 1);
   //                     meeting.participants.push(this.username);
                })
                .catch(response => console.log(response.status));
            },
            deleteMeeting(meeting) {
                this.$http.delete(`meetings/${meeting.id}`)
                    .then(response => {
                        this.meetings.splice(this.meetings.indexOf(meeting), 1);
                })
                .catch(response => console.log(response.status));
            },
            getMeetings(){
                this.$http.get('meetings')
                    .then(response => {
                       for (let i=0; i<response.body.length; i++){
                        response.body[i].participants=[];
                        this.meetings.push(response.body[i]);
                        }
//                       console.log(response.body[i]);
                    })
                   .catch(response => console.log(response.status))
            }
        },
        beforeMount(){
                this.getMeetings()
        }
    }
</script>