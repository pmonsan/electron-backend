/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgBatchComponent } from 'app/entities/pg-batch/pg-batch.component';
import { PgBatchService } from 'app/entities/pg-batch/pg-batch.service';
import { PgBatch } from 'app/shared/model/pg-batch.model';

describe('Component Tests', () => {
  describe('PgBatch Management Component', () => {
    let comp: PgBatchComponent;
    let fixture: ComponentFixture<PgBatchComponent>;
    let service: PgBatchService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgBatchComponent],
        providers: []
      })
        .overrideTemplate(PgBatchComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgBatchComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgBatchService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgBatch(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgBatches[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
