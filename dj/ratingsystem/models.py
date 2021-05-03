from django.db import models

# Create your models here.

class Review(models.Model):
    class Meta:
            db_table = "review"

    id = models.BigAutoField(primary_key=True)
    reviewer_name = models.CharField(max_length=100)
    rating = models.IntegerField(default=0)
    title = models.CharField(max_length=200)#
    comment = models.TextField()
    created_on = models.DateTimeField(auto_now=True)
