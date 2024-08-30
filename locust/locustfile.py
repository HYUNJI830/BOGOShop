from locust import HttpUser, TaskSet, task, between

class ItemPageUserBehavior(TaskSet):

    @task
    def get_item_page(self):
        # API 호출
        with self.client.get("/api/items/Page", params={"page": 0, "size": 10}, catch_response=True) as response:
            if response.status_code == 200:
                response.success()
            else:
                response.failure(f"Failed to load items with status code: {response.status_code}")

class WebsiteUser(HttpUser):
    tasks = [ItemPageUserBehavior]
    wait_time = between(1, 3)  # 각 요청 사이의 대기 시간 (초)

