from django.db import models

# Create your models here.

class Review(models.Model):
    class Meta:
            db_table = "review"

    id = models.BigAutoField(primary_key=True)
    reviewer_name = models.CharField(max_length=100)
    user_id = models.CharField(max_length=20)
    rating = models.IntegerField(default=0)
    title = models.CharField(max_length=200)#
    comment = models.TextField()
    is_approved = models.BooleanField(default=False)
    created_on = models.DateTimeField(auto_now=True)
    updated_on = models.DateTimeField(auto_now=True)
