/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PgMessageModelComponent } from 'app/entities/pg-message-model/pg-message-model.component';
import { PgMessageModelService } from 'app/entities/pg-message-model/pg-message-model.service';
import { PgMessageModel } from 'app/shared/model/pg-message-model.model';

describe('Component Tests', () => {
  describe('PgMessageModel Management Component', () => {
    let comp: PgMessageModelComponent;
    let fixture: ComponentFixture<PgMessageModelComponent>;
    let service: PgMessageModelService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PgMessageModelComponent],
        providers: []
      })
        .overrideTemplate(PgMessageModelComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PgMessageModelComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PgMessageModelService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new PgMessageModel(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.pgMessageModels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
