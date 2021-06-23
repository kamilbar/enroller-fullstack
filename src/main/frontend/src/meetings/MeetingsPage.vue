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

    <span v-if="loadedMeetings.length == 0">
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
                loadedMeetings: []
            };
        },
        methods: {
            addNewMeeting(meeting) {
                    this.$http.post('meetings', meeting)
                        .then(response => {
                        this.loadedMeetings.push(meeting);
                    })
                   // .catch(response => this.failure('Błąd przy zakładaniu konta. Kod odpowiedzi: ' + response.status));
            },
            addMeetingParticipant(meeting) {
                meeting.participants.push(this.username);
            },
            removeMeetingParticipant(meeting) {
                meeting.participants.splice(meeting.participants.indexOf(this.username), 1);
            },
            deleteMeeting(meeting) {
                this.meetings.splice(this.meetings.indexOf(meeting), 1);
            },
            getMeetings(){
                this.$http.get('meetings')
                    .then(response => {
                       for (let i=0; i<response.body.length; i++){
                        //    console.log("wartosc i: " + i);
                        //    console.log("wartosc i: " + response.body[i]);
                        //    console.log(response.body[i]);
                        this.loadedMeetings.push(response.body[i]);
                        // console.log("wartosc loadedMeetings: " + this.loadedMeetings.[i]);
                       }
                       console.log(response.body[i]);
                        // this.loadedMeetings.push(response.body[i])
                        // this.componentKey +=1;
                    })
                    .catch(response => console.log(response.status))
            }
        },
 //       beforeMount(){
 //               this.getMeetings()
  //      }
    }
</script>
