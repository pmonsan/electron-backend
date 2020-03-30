/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelDataComponent } from 'app/entities/pg-message-model-data/pg-message-model-data.component';
import { PgMessageModelDataService } from 'app/entities/pg-message-model-data/pg-message-model-data.service';
import { PgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

describe('Component Tests', () => {
  describe('PgMessageModelData Management Component', () => {
    let comp: PgMessageModelDataComponent;
    let fixture: ComponentFixture<PgMessageModelDataComponent>;
    let service: PgMessageModelDataService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelDataComponent],
        providers: []
      })
        .overrideTemplate(PgMessageModelDataComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageModelDataComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelDataService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgMessageModelData(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgMessageModelData[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
