from locust import HttpUser, TaskSet, task, between
import random

class OrderQueryTasks(TaskSet):

    @task
    def get_orders_v4(self):
        offset = random.choice([0, 10, 20, 30, 40, 50])
        limit = random.choice([1, 5, 10, 20, 50])

        with self.client.get(f"/api/orders/v4?offset={offset}&limit={limit}", headers=headers, catch_response=True) as response:
            if response.status_code != 200:
                response.failure(f"Failed request: Offset={offset}, Limit={limit}")
            else:
                response.success()

class WebsiteUser(HttpUser):
    tasks = [OrderQueryTasks]
    wait_time = between(1, 3)
    host = "http://localhost:8080"
